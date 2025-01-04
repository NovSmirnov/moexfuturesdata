package org.smirnovav.moexFuturesData.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.smirnovav.moexFuturesData.components.TempFilesCollector;
import org.smirnovav.moexFuturesData.service.DownloadFuturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/download")
@Tag(name = "Контроллер загрузок", description = "Скачивание различных служебных файлов и отчетов")
public class DownloadController {

    private DownloadFuturesService downloadFuturesService;
    private TempFilesCollector tempFilesCollector;

    @Autowired
    public DownloadController(DownloadFuturesService downloadFuturesService, TempFilesCollector tempFilesCollector) {
        this.tempFilesCollector = tempFilesCollector;
        this.downloadFuturesService = downloadFuturesService;
    }

    @GetMapping("/instrinfo")
    @ResponseBody
    @Operation(summary = "Получение актуального служебного файла .csv для работы приложения AlgoOptimizer",
            description = "Возвращает .csv файл содежащий актуальную информацию о фьючерсах. В первой колонке " +
                    "asset code фьючерса, во второй комиссия операции купли/продажи в рублях с учетом проскальзывания в 1 пункт" +
                    ", в третьей  гарантийное обеспечение, в четвертой стоимость одного минимального шага цены в рублях, " +
                    "в пятой минимальный шаг цены в котировке")
    public ResponseEntity<Resource> downloadOneFile(
            @RequestParam(value = "assetcodes", required = false, defaultValue = "all")
            @Parameter(description = "Asset codes фьючерсов, по которым будут заполнятся строки выводимого файла" +
                    ", в запросе можно ввести несколько значений, при отсутствии параметра в запросе, файл заполняется " +
                    "по всем asset codes из базы данных сервиса")
            List<String> assetCodes,
            @RequestParam(value = "brokerfee", required = false, defaultValue = "1")
            @Parameter(description = "Брокерская комиссия за операцию открытия или закрытия позиции по фьючерсу в" +
                    " рублях.")
            double brokersFee,
            @RequestParam(value = "averaging", required = false, defaultValue = "0")
            @Parameter(description = "Период усреднения показателя показателя ликвидности в днях, который используется " +
                    "для сортировки выводимых строк файла от большего к меньшему, если параметр не выставляется или " +
                    "равен 0, то сортировка не производится")
            int averaging
    ) {
        File tempFile = null;
        String fileData;
        if (assetCodes.get(0).equals("all")) {
            if (averaging == 0) {
                fileData = downloadFuturesService.allShortFuturesInfoForCsvFile(brokersFee);
            } else {
                fileData = downloadFuturesService.allLiqSortedShortFuturesInfoForCsvFile(assetCodes, brokersFee, averaging);
            }
        } else {
            if (averaging == 0) {
                fileData = downloadFuturesService.shortFuturesInfoForCsvFile(assetCodes, brokersFee);
            } else {
                fileData = downloadFuturesService.allLiqSortedShortFuturesInfoForCsvFile(assetCodes, brokersFee, averaging);
            }
        }
        try (FileWriter writer = new FileWriter(tempFile = File.createTempFile("InstrInfo", ".csv"))) {
            writer.write(fileData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.deleteOnExit();
        Path filePath = Paths.get(tempFile.getPath());
        Resource fileResource = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString()  + "\"");
        tempFilesCollector.addNewTempFile(tempFile);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileResource);
    }




}
