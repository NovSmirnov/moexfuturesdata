package org.smirnovav.moexFuturesData.calculations.collectors.comparators;


import org.smirnovav.moexFuturesData.calculations.collectors.futures.AverageLiquidity;

import java.util.Comparator;

public class AvLiquidityValueComparator implements Comparator<AverageLiquidity> {
    @Override
    public int compare(AverageLiquidity avLiq1, AverageLiquidity avLiq2) {
        long value1 = avLiq1.getAverageValueLiquidity();
        long value2 = avLiq2.getAverageValueLiquidity();
        return Long.compare(value1, value2);
    }
}
