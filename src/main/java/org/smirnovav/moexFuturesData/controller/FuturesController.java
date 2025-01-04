package org.smirnovav.moexFuturesData.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.smirnovav.moexFuturesData.calculations.collectors.futures.AverageLiquidity;
import org.smirnovav.moexFuturesData.calculations.collectors.futures.FuturesFullSpecification;
import org.smirnovav.moexFuturesData.calculations.collectors.futures.VolatilityLiquidity;
import org.smirnovav.moexFuturesData.calculations.enums.LiquidityType;
import org.smirnovav.moexFuturesData.calculations.enums.VolatilityType;
import org.smirnovav.moexFuturesData.db.entity.IntegratedFuturesInfoEntity;
import org.smirnovav.moexFuturesData.dto.IntegratedFuturesInfoDto;
import org.smirnovav.moexFuturesData.mapper.FuturesMapper;
import org.smirnovav.moexFuturesData.service.DbService;
import org.smirnovav.moexFuturesData.service.ResponseFuturesService;
import org.smirnovav.moexFuturesData.utils.MoexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
@RequestMapping("/")
@Tag(name = "Контроллер фьючерсов", description = "Операции по получению информации о фьючерсах Мосбиржи")
public class FuturesController {

    private DbService dbService;
    private ResponseFuturesService responseFuturesService;

    @Autowired
    public FuturesController(DbService dbService, ResponseFuturesService responseFuturesService) {
        this.dbService = dbService;
        this.responseFuturesService = responseFuturesService;
    }

    @Operation(summary = "Запрос детальной спецификации фьючерса",
            description = "Возвращает актуальную детальную спецификацию фьючерса по краткому " +
                    "наименованию фьючерса указанному в качестве параметра shortname" +
                    ", например shortname=Si-12.25. В случае отсутствия параметра, неверно " +
                    "указанного параметра или отсутствия значения параметра в базе данных, " +
                    "возвращается пустой объект.")
    @GetMapping("/futspec")
    @ResponseBody
    public FuturesFullSpecification getFutFullSpec(
            @RequestParam(value = "shortname", required = false, defaultValue = "defaultValue")
            @Parameter(description = "Краткое наименование фьючерса, например Si-12.25") String shortName) {
        if (shortName.equals("defaultValue") || !dbService.isTickerExists(shortName)) {
            return new FuturesFullSpecification();
        } else {
            return responseFuturesService.getFutFullSpecByShortName(shortName);
        }
    }

    @Operation(summary = "Список сортированных по ликвидности фьючерсов",
            description = "Возвращает список фьючерсов отсортированных по ликвидности по убыванию")
    @GetMapping("/liquidity")
    @ResponseBody
    public List<AverageLiquidity> liquidity(
            @RequestParam(value = "averaging", required = false, defaultValue = "7")
            @Parameter(description = "Период усреднения показателя показателя ликвидности в днях")
            int averagingPeriodInDays,
            @RequestParam(value = "liqtype", required = false, defaultValue = "VAL")
            @Parameter(description = "Тип ликвидности: val - в рублях, vol - в лотах") LiquidityType liquidityType,
            @RequestParam(value = "posnum", required = false, defaultValue = "20")
            @Parameter(description = "Количество выводимых позиций")
            int posNum,
            @RequestParam(value = "startpos", required = false, defaultValue = "0")
            @Parameter(description = "Позиция, с которой начинается вывод")
            int startPos
    ) {
        if (averagingPeriodInDays < 1 || posNum < 0 || startPos < 0) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
        if (posNum >= 100)
            posNum = 100;
        List<AverageLiquidity> allLiquidities = responseFuturesService.getAllAverageLiquidities(averagingPeriodInDays, liquidityType);
        List<AverageLiquidity> liquiditiesForOutput = new ArrayList<>();
        if (!allLiquidities.isEmpty()) {
            int realStartPos = startPos;
            int finishPos = startPos + posNum;
            if (startPos >= allLiquidities.size())
                realStartPos = allLiquidities.size();
            if (finishPos >= allLiquidities.size())
                finishPos = allLiquidities.size();
            for (int i = realStartPos; i < finishPos; i++) {
                liquiditiesForOutput.add(allLiquidities.get(i));
            }
        }
        return liquiditiesForOutput;
    }


    @Operation(summary = "Список сортированных по волатильности фьючерсов",
            description = "Возвращает список фьючерсов отсортированных по волатильности по убыванию среди" +
                    " n-го количества самых ликвидных фьючерсов. Волатильность выражается как в показателе ATR %? так" +
                    " и в показателе ATR % * кредитное плечо")
    @GetMapping("/volatility")
    @ResponseBody
    public List<VolatilityLiquidity> volatility(
            @RequestParam(value = "averaging", required = false, defaultValue = "7")
            @Parameter(description = "Период усреднения показателя показателя ликвидности в днях")
            int averagingLiqPeriodInDays,
            @RequestParam(value = "atrperiod", required = false, defaultValue = "7")
            @Parameter(description = "Период для расчета показателя ATR")
            int atrPeriod,
            @RequestParam(value = "voltype", required = false, defaultValue = "VOL")
            @Parameter(description = "Тип волатильности: vol - ATR %, lev - ATR % * leverage")
            VolatilityType volatilityType,
            @RequestParam(value = "liqtype", required = false, defaultValue = "VAL")
            @Parameter(description = "Тип ликвидности: val - в рублях, vol - в лотах")
            LiquidityType liquidityType,
            @RequestParam(value = "startpos", required = false, defaultValue = "0")
            @Parameter(description = "Позиция, с которой начинается вывод")
            int startPos,
            @RequestParam(value = "posnum", required = false, defaultValue = "20")
            @Parameter(description = "Количество выводимых позиций")
            int posNum) {
        return responseFuturesService.getAllVolatilityLiquidities(averagingLiqPeriodInDays, atrPeriod,
                volatilityType, liquidityType, startPos, posNum);
    }

    @Operation(summary = "Список спецификаций ближайших фьючерсов",
            description = "Возвращает список спецификаций ближайших фьючерсов без сортировки." +
                    " Ближайшим считается тот фьючерс из Asset code у которого наиболее ранняя дата экспирации относительно " +
                    "сегодняшнего дня.")
    @GetMapping("/nearests")
    @ResponseBody
    public List<FuturesFullSpecification> getAllNearestFutures(
            @RequestParam(value = "startpos", required = false, defaultValue = "0")
            @Parameter(description = "Позиция, с которой начинается вывод")
            int startPos,
            @RequestParam(value = "posnum", required = false, defaultValue = "20")
            @Parameter(description = "Количество выводимых позиций")
            int posNum,
            @RequestParam(value = "assetcode", required = false, defaultValue = "all")
            @Parameter(description = "Asset code фьючерса, по которому нужно найти фьючерс с ближайшей" +
                            "датой экспирации, например GAZR. Если Asset code указан неверно, то возвращен" +
                    " будет объект JSON с нулевыми полями")
            String assetCode){
        return responseFuturesService.findNearestFuturesList(startPos, posNum, assetCode);
    }




    @Hidden
    @GetMapping
    @ResponseBody
    public String addDataToBase() {
        Calendar startDate = new GregorianCalendar(2024, 8, 1, 12, 0 ,0);
        Calendar finishDate = new GregorianCalendar();

        List<IntegratedFuturesInfoDto> integratedFuturesInfoDtos = MoexUtils.getAllTradingFuturesInfo(startDate, finishDate);
        List<IntegratedFuturesInfoEntity> integratedFuturesInfoEntities = FuturesMapper.integratedInfoDtoListToEntities(integratedFuturesInfoDtos);
        dbService.saveAllFuturesData(integratedFuturesInfoEntities);
        return "Ok!";
    }

    @Hidden
    @Operation(summary = "Полная очистка базы данных!", description = "Полностью очищает базу данных")
    @GetMapping("/clean")
    @ResponseBody
    public String clean() {
//        dbService.cleanFuturesRepositories();
        dbService.cleanDb();
        return "DB is clean!";
    }

    @Hidden
    @GetMapping("/write")
    @ResponseBody
    public String writeOneRow() {
        Calendar startDate = new GregorianCalendar(2024, 8, 1, 12, 0 ,0);
        Calendar finishDate = new GregorianCalendar();

        IntegratedFuturesInfoDto integratedFuturesInfoDto = MoexUtils.getIntegratedFuturesInfo("BYM5", startDate, finishDate);
        System.out.println(integratedFuturesInfoDto.getFuturesComplexInfo().getSpecification().getAssetCode());
        IntegratedFuturesInfoEntity integratedFuturesInfoEntity = FuturesMapper.integratedInfoDtoToEntity(integratedFuturesInfoDto);
        dbService.saveFuturesData(integratedFuturesInfoEntity);

        integratedFuturesInfoDto = MoexUtils.getIntegratedFuturesInfo("SRM5", startDate, finishDate);
        System.out.println(integratedFuturesInfoDto.getFuturesComplexInfo().getSpecification().getAssetCode());
        integratedFuturesInfoEntity = FuturesMapper.integratedInfoDtoToEntity(integratedFuturesInfoDto);
        dbService.saveFuturesData(integratedFuturesInfoEntity);

        return "Written!";
    }

    @Hidden
    @GetMapping("/load")
    @ResponseBody
    public List<IntegratedFuturesInfoDto> loadEntity() {
//        IntegratedFuturesInfoDto integratedFuturesInfoDto = dbService.getIntegratedFuturesInfoDto("GAZR-12.24");
//        return integratedFuturesInfoDto;

        StringBuilder builder = new StringBuilder();
        List<IntegratedFuturesInfoDto> integratedFuturesInfoDtos = dbService.getAllIntegratedFuturesInfoDto();
        for (IntegratedFuturesInfoDto integratedFuturesInfoDto : integratedFuturesInfoDtos) {
            builder.append(integratedFuturesInfoDto.getFuturesComplexInfo().getSpecification().toString()).append("\n");
        }
        return integratedFuturesInfoDtos;

    }
    @Hidden
    @GetMapping("/loadone")
    @ResponseBody
    public IntegratedFuturesInfoDto loadOneDto(@RequestParam(value = "shortname", required = false, defaultValue = "Si-12.24") String shortName) {
        IntegratedFuturesInfoDto integratedFuturesInfoDto = dbService.getIntegratedFuturesInfoDto(shortName);
        return integratedFuturesInfoDto;
    }


    @Hidden
    @Operation(summary = "Удаление старых фьючерсов", description = "Проверка фьючерсов и удаление старых")
    @GetMapping("/delold")
    @ResponseBody
    public String cleanOldFutures() {
        dbService.deleteOldInformation();
        return "Old futures were deleted!";
    }

    @Hidden
    @GetMapping("/update")
    @ResponseBody
    public String updateDb() {
//        addDataToBase();
        Calendar startDate = new GregorianCalendar(2024, 8, 1, 12, 0 ,0);
        Calendar finishDate = new GregorianCalendar();

        List<IntegratedFuturesInfoDto> integratedFuturesInfoDtos = MoexUtils.getAllTradingFuturesInfo(startDate, finishDate);
        List<IntegratedFuturesInfoEntity> integratedFuturesInfoEntities = FuturesMapper.integratedInfoDtoListToEntities(integratedFuturesInfoDtos);
        dbService.saveAllFuturesData(integratedFuturesInfoEntities);

        dbService.deleteOldInformation();
        System.out.println("DB was updated!");
        return "DB was updated!";
    }

    @Hidden
    @GetMapping("/delete")
    @ResponseBody
    public String deleteOne() {
        Calendar startDate = new GregorianCalendar(2024, 8, 1, 12, 0 ,0);
        Calendar finishDate = new GregorianCalendar();

        IntegratedFuturesInfoDto dto =  MoexUtils.getIntegratedFuturesInfo("GDZ4", startDate, finishDate);
        IntegratedFuturesInfoEntity entity = FuturesMapper.integratedInfoDtoToEntity(dto);
        dbService.deleteTicker(entity.getFuturesEntity());
        dto =  MoexUtils.getIntegratedFuturesInfo("GZZ4", startDate, finishDate);
        entity = FuturesMapper.integratedInfoDtoToEntity(dto);
        dbService.deleteTicker(entity.getFuturesEntity());
        return "Futures was deleted!";
    }

    @Hidden
    @GetMapping("/test")
    @ResponseBody
    public List<AverageLiquidity> test() {
        return responseFuturesService.getAllAverageLiquidities(7, LiquidityType.VAL);
//        return dbService.getAllFuturesShortNames();
//        return List.of("Str");
    }









}
