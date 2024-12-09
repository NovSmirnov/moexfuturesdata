package org.smirnovav.moexFuturesData.controller;

import org.smirnovav.moexFuturesData.db.entity.IntegratedFuturesInfoEntity;
import org.smirnovav.moexFuturesData.dto.IntegratedFuturesInfoDto;
import org.smirnovav.moexFuturesData.mapper.FuturesMapper;
import org.smirnovav.moexFuturesData.service.DbService;
import org.smirnovav.moexFuturesData.utils.MoexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
@RequestMapping("/")
public class FuturesController {

    private DbService dbService;

    @Autowired
    public FuturesController(DbService dbService) {
        this.dbService = dbService;
    }

    @GetMapping
    @ResponseBody
    public String addDataToBase() {
        Calendar startDate = new GregorianCalendar(2024, 8, 1, 12, 0 ,0);
        Calendar finishDate = new GregorianCalendar();

//        List<IntegratedFuturesInfoDto> integratedFuturesInfoDtos = MoexUtils.getAllTradingFuturesInfo(startDate, finishDate);

        IntegratedFuturesInfoDto integratedFuturesInfoDto = MoexUtils.getIntegratedFuturesInfo("GZZ4", startDate, finishDate);
        System.out.println(integratedFuturesInfoDto.getFuturesComplexInfo().getSpecification().getAssetCode());
        IntegratedFuturesInfoEntity integratedFuturesInfoEntity = FuturesMapper.integratedInfoDtoToEntity(integratedFuturesInfoDto);

//        List<IntegratedFuturesInfoEntity> integratedFuturesInfoEntities = new ArrayList<>();

        dbService.saveDataAlt(integratedFuturesInfoEntity);
//        dbService.cleanDb();
//        return dbService.getById();
        return "Ok!";
    }

    @GetMapping("/clean")
    @ResponseBody
    public String clean() {
        dbService.cleanDb();
        return "DB is clean!";
    }






}
