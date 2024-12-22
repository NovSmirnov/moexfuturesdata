package org.smirnovav.moexFuturesData.calculations.calcutils;

import org.smirnovav.moexFuturesData.calculations.collectors.comparators.AvLiquidityValueComparator;
import org.smirnovav.moexFuturesData.calculations.collectors.comparators.AvLiquidityVolumeComparator;
import org.smirnovav.moexFuturesData.calculations.collectors.futures.AverageLiquidity;
import org.smirnovav.moexFuturesData.calculations.enums.LiquidityType;
import org.smirnovav.moexFuturesData.dto.IntegratedFuturesInfoDto;
import org.smirnovav.moexFuturesData.service.DbService;
import org.smirnovav.moex_lib.collectors.futures.FuturesDayHistoryInfo;
import org.smirnovav.moex_lib.comparators.futureshictorycomparators.DateFuturesHistoryComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
public class LiquidityUtils {

    private DbService dbService;

    @Autowired
    public LiquidityUtils(DbService dbService) {
        this.dbService = dbService;
    }

    public AverageLiquidity getAverageLiquidity(String shortName, int averagingPeriodInDays) {
        IntegratedFuturesInfoDto integratedFuturesInfoDto = dbService.getIntegratedFuturesInfoDto(shortName);
        List<FuturesDayHistoryInfo> history = integratedFuturesInfoDto.getFuturesDayHistoryInfoList();
        if (!history.isEmpty()) {
            history.sort(new DateFuturesHistoryComparator());
            Collections.reverse(history);
            Calendar lastTradeDate = history.get(0).getTradeDate();
            int realAveragingPeriod;
            if (history.size() >= averagingPeriodInDays) {
                realAveragingPeriod = averagingPeriodInDays;
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

            return new AverageLiquidity(avVolume, avValue, realAveragingPeriod,
                    integratedFuturesInfoDto.getFuturesComplexInfo().getSpecification().getSecId(),
                    shortName, lastTradeDate);
        } else {
            return new AverageLiquidity(0, 0, 0, null,
                    null, null);
        }
    }

    public List<AverageLiquidity> getAllFuturesAverageLiquidityList(int averagingPeriodInDays, LiquidityType sortByLiquidityType) {
        List<String> allShortNames = dbService.getAllFuturesShortNames();
        List<AverageLiquidity> averageLiquidities = new ArrayList<>();
        for (String shortName : allShortNames) {
            averageLiquidities.add(getAverageLiquidity(shortName, averagingPeriodInDays));
        }
        switch (sortByLiquidityType) {
            case VAL:
                averageLiquidities.sort(new AvLiquidityValueComparator());
                Collections.reverse(averageLiquidities);
                return averageLiquidities;
            case VOL:
                averageLiquidities.sort(new AvLiquidityVolumeComparator());
                Collections.reverse(averageLiquidities);
                return averageLiquidities;
            default:
                return averageLiquidities;
        }
    }


}
