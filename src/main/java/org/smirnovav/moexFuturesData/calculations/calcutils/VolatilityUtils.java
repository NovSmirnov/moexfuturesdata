package org.smirnovav.moexFuturesData.calculations.calcutils;

import org.smirnovav.moexFuturesData.calculations.calcutils.indicators.AtrIndicator;
import org.smirnovav.moexFuturesData.calculations.collectors.comparators.*;
import org.smirnovav.moexFuturesData.calculations.collectors.futures.VolatilityLiquidity;
import org.smirnovav.moexFuturesData.calculations.enums.LiquidityType;
import org.smirnovav.moexFuturesData.calculations.enums.VolatilityType;
import org.smirnovav.moexFuturesData.dto.IntegratedFuturesInfoDto;
import org.smirnovav.moexFuturesData.service.DbService;
import org.smirnovav.moex_lib.collectors.futures.FuturesDayHistoryInfo;
import org.smirnovav.moex_lib.comparators.futureshictorycomparators.DateFuturesHistoryComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VolatilityUtils {

    private DbService dbService;

    @Autowired
    public VolatilityUtils(DbService dbService) {
        this.dbService = dbService;
    }

    public VolatilityLiquidity getVolatilityLiquidity(String shortName, int averagingLiqPeriodInDays, int atrPeriod) {
        IntegratedFuturesInfoDto integratedFuturesInfoDto = dbService.getIntegratedFuturesInfoDto(shortName);
        List<FuturesDayHistoryInfo> history = integratedFuturesInfoDto.getFuturesDayHistoryInfoList();
        if (!history.isEmpty()) {
            history.sort(new DateFuturesHistoryComparator());
            List<Double> atrValues = Arrays.stream(AtrIndicator.atrInPrcWithoutZerosFromMoexCandleList(history, atrPeriod))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            List<Double> atrLeverageValues = new ArrayList<>();
            List<Calendar> dates = new ArrayList<>();
            for (int i = 0; i < atrValues.size(); i++) {
                double leverage = integratedFuturesInfoDto.getFuturesComplexInfo().getMarketData().getLast() /
                        integratedFuturesInfoDto.getFuturesComplexInfo().getSpecification().getInitialMargin() *
                        integratedFuturesInfoDto.getFuturesComplexInfo().getSpecification().getStepPrice() /
                        integratedFuturesInfoDto.getFuturesComplexInfo().getSpecification().getMinStep();
                atrLeverageValues.add(atrValues.get(i) * leverage);
                dates.add(history.get(i).getTradeDate());
            }
            Collections.reverse(history);
            Calendar lastTradeDate = history.get(0).getTradeDate();
            int realAveragingPeriod;
            if (history.size() >= averagingLiqPeriodInDays) {
                realAveragingPeriod = averagingLiqPeriodInDays;
            } else {
                realAveragingPeriod = history.size();
            }
            long totalValue = 0;
            long totalVolume = 0;
            for (int i = 0; i < realAveragingPeriod; i++) {
                totalValue += (long) history.get(i).getValue();
                totalVolume += history.get(i).getVolume();
            }
            long avValue = totalValue / realAveragingPeriod;
            long avVolume = totalVolume / realAveragingPeriod;

            return new VolatilityLiquidity(avVolume, avValue, averagingLiqPeriodInDays,
                    integratedFuturesInfoDto.getFuturesComplexInfo().getSpecification().getSecId(),
                    shortName, lastTradeDate, atrPeriod, atrValues, atrLeverageValues, dates);
        } else {
            return new VolatilityLiquidity(0, 0, 0, null,
                    null, null, 0, null, null, null);
        }
    }

    public List<VolatilityLiquidity> getAllFuturesVolatilityLiquidityList(int averagingLiqPeriodInDays, int atrPeriod,
                                                                          VolatilityType volatilityType, LiquidityType liquidityType,
                                                                          int startPosition, int numberOfPositions
                                                                          ) {
        List<String> allShortNames = dbService.getAllFuturesShortNames();
        List<VolatilityLiquidity> volatilityLiquidityList = new ArrayList<>();
        for (String shortName : allShortNames) {
            volatilityLiquidityList.add(getVolatilityLiquidity(shortName, averagingLiqPeriodInDays, atrPeriod));
        }
        volatilityLiquidityList.sort(liquidityType == LiquidityType.VAL ? new AvLiquidityValueComparator() :
                new AvLiquidityVolumeComparator());
        Collections.reverse(volatilityLiquidityList);
        int realStartPosition;
        int finishPosition;
        if (startPosition < volatilityLiquidityList.size()) {
            realStartPosition = startPosition;
        } else {
            realStartPosition = volatilityLiquidityList.size() - 1;
        }
        if (realStartPosition + numberOfPositions < volatilityLiquidityList.size()) {
            finishPosition = realStartPosition + numberOfPositions;
        } else {
            finishPosition = volatilityLiquidityList.size();
        }

        List<VolatilityLiquidity> shortList = volatilityLiquidityList.subList(realStartPosition, finishPosition);

        shortList.sort(volatilityType == VolatilityType.VOL ? new VolatilityComparator() : new VolatilityLeverageComparator());
        Collections.reverse(shortList);

//        for (VolatilityLiquidity volatilityLiquidity : shortList) {
//            VolatilityLiquidity.VolatilityChartData c = volatilityLiquidity.getVolatilityChartData();
//            System.out.println(c.getAtrValues().size() + " - " + c.getDates().size());
//        }

        return shortList;
    }



}
