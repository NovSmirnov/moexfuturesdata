package org.smirnovav.moexFuturesData.service;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Min;
import org.smirnovav.moexFuturesData.calculations.calcutils.LiquidityUtils;
import org.smirnovav.moexFuturesData.calculations.calcutils.VolatilityUtils;
import org.smirnovav.moexFuturesData.calculations.collectors.futures.AverageLiquidity;
import org.smirnovav.moexFuturesData.calculations.collectors.futures.FuturesFullSpecification;
import org.smirnovav.moexFuturesData.calculations.collectors.futures.VolatilityLiquidity;
import org.smirnovav.moexFuturesData.calculations.enums.LiquidityType;
import org.smirnovav.moexFuturesData.calculations.enums.VolatilityType;
import org.smirnovav.moexFuturesData.db.entity.IntegratedFuturesInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseFuturesService {

    private DbService dbService;
    private LiquidityUtils liquidityUtils;

    private VolatilityUtils volatilityUtils;

    @Autowired
    public ResponseFuturesService(DbService dbService, LiquidityUtils liquidityUtils, VolatilityUtils volatilityUtils) {
        this.dbService = dbService;
        this.liquidityUtils = liquidityUtils;
        this.volatilityUtils = volatilityUtils;
    }

    public FuturesFullSpecification getFutFullSpecByShortName(String shortName) {
        return dbService.getFullFutSpecByShortName(shortName);
    }

    public List<AverageLiquidity> getAllAverageLiquidities(int averagingPeriodInDays, LiquidityType sortByLiquidityType) {
        return liquidityUtils.getAllFuturesAverageLiquidityList(averagingPeriodInDays, sortByLiquidityType);
    }

    public List<VolatilityLiquidity> getAllVolatilityLiquidities(int averagingLiqPeriodInDays, int atrPeriod,
                                                                 VolatilityType volatilityType, LiquidityType liquidityType,
                                                                 int startPosition, int numberOfPositions) {
        if (numberOfPositions > 100)
            numberOfPositions = 100;
        List<VolatilityLiquidity> volatilityLiquidityList = volatilityUtils
                .getAllFuturesVolatilityLiquidityList(averagingLiqPeriodInDays, atrPeriod,
                volatilityType, liquidityType, startPosition, numberOfPositions);
        return volatilityLiquidityList;
    }
}
