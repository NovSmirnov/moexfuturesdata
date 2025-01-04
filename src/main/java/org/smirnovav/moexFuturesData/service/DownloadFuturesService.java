package org.smirnovav.moexFuturesData.service;

import org.smirnovav.moexFuturesData.calculations.calcutils.LiquidityUtils;
import org.smirnovav.moexFuturesData.calculations.collectors.comparators.AvLiquidityValueComparator;
import org.smirnovav.moexFuturesData.calculations.collectors.futures.AverageLiquidity;
import org.smirnovav.moexFuturesData.calculations.collectors.futures.FuturesFullSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DownloadFuturesService {

    private DbService dbService;
    private LiquidityUtils liquidityUtils;

    @Autowired
    public DownloadFuturesService(DbService dbService, LiquidityUtils liquidityUtils) {
        this.dbService = dbService;
        this.liquidityUtils = liquidityUtils;
    }

    public String shortFuturesInfoForCsvFile(List<String> assetCodes, double brokersFee) {
        StringBuilder builder = new StringBuilder();

        if (assetCodes.isEmpty()) {
            List<FuturesFullSpecification> specifications = dbService.findAllNearestFutures();
            for(FuturesFullSpecification specification : specifications) {
                builder.append(getShortFuturesCsvFileRow(specification, brokersFee)).append("\n");
            }
            return builder.toString();
        } else {
            List<FuturesFullSpecification> specifications = new ArrayList<>();
            for (String assetCode : assetCodes) {
                specifications.add(dbService.findNearestFuturesByAssetCode(assetCode));
            }
            for(FuturesFullSpecification specification : specifications) {
                builder.append(getShortFuturesCsvFileRow(specification, brokersFee)).append("\n");
            }
            return builder.toString();
        }
    }

    public String allShortFuturesInfoForCsvFile(double brokersFee) {
        List<String> assetCodes = dbService.getAllAssetCodes();
        if (!assetCodes.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            List<FuturesFullSpecification> specifications = dbService.findAllNearestFutures();
            for(FuturesFullSpecification specification : specifications) {
                builder.append(getShortFuturesCsvFileRow(specification, brokersFee)).append("\n");
            }
            return builder.toString();
        } else {
            return "";
        }
    }

    public String getShortFuturesCsvFileRow(FuturesFullSpecification futuresFullSpecification, double brokersFee) {
        StringBuilder builder = new StringBuilder();
        String assetCode = futuresFullSpecification.getAssetCode();
        double feePlusSlippage = (brokersFee + futuresFullSpecification.getBuySellFee() +
                futuresFullSpecification.getStepPrice()) / futuresFullSpecification.getStepPrice() *
                futuresFullSpecification.getMinStep();
        double initialMargin = futuresFullSpecification.getInitialMargin();
        double minStep = futuresFullSpecification.getMinStep();
        double roublesInWholeUnit = futuresFullSpecification.getStepPrice() / minStep;

        DecimalFormat decimalFormat5 = new DecimalFormat("#######.#####");
        DecimalFormat decimalFormat2 = new DecimalFormat("#######.##");

        BigDecimal feePlusSlippageBd = BigDecimal.valueOf(feePlusSlippage)
                .setScale(futuresFullSpecification.getDecimals(), RoundingMode.HALF_UP);

        return assetCode + ";" + feePlusSlippageBd + ";" + decimalFormat2.format(initialMargin) + ";" +
                decimalFormat5.format(roublesInWholeUnit) + ";" + decimalFormat5.format(minStep);
    }

    public String allLiqSortedShortFuturesInfoForCsvFile(List<String> assetCodes, double brokersFee, int averagingPeriodInDays) {

        StringBuilder builder = new StringBuilder();

        if (assetCodes.isEmpty() || assetCodes.get(0).equals("all")) {
            List<FuturesFullSpecification> specifications = dbService.findAllNearestFutures();
            List<AverageLiquidity> liquidities = new ArrayList<>();
            for(FuturesFullSpecification specification : specifications) {
                liquidities.add(liquidityUtils.getAverageLiquidity(specification.getShortName(), averagingPeriodInDays));
            }
            liquidities.sort(new AvLiquidityValueComparator());
            Collections.reverse(liquidities);
            for (AverageLiquidity averageLiquidity : liquidities) {
                for (FuturesFullSpecification futuresFullSpecification : specifications) {
                    if (averageLiquidity.getShortName().equals(futuresFullSpecification.getShortName())) {
                        builder.append(getShortFuturesCsvFileRow(futuresFullSpecification, brokersFee)).append("\n");
                    }
                }
            }
            return builder.toString();
        } else {
            List<FuturesFullSpecification> specifications = new ArrayList<>();
            for (String assetCode : assetCodes) {
                specifications.add(dbService.findNearestFuturesByAssetCode(assetCode));
            }
            List<AverageLiquidity> liquidities = new ArrayList<>();
            for(FuturesFullSpecification specification : specifications) {
                liquidities.add(liquidityUtils.getAverageLiquidity(specification.getShortName(), averagingPeriodInDays));
            }
            liquidities.sort(new AvLiquidityValueComparator());
            Collections.reverse(liquidities);

            for (AverageLiquidity averageLiquidity : liquidities) {
                for (FuturesFullSpecification futuresFullSpecification : specifications) {
                    if (averageLiquidity.getShortName().equals(futuresFullSpecification.getShortName())) {
                        builder.append(getShortFuturesCsvFileRow(futuresFullSpecification, brokersFee)).append("\n");
                    }
                }
            }
            return builder.toString();
        }
    }
}
