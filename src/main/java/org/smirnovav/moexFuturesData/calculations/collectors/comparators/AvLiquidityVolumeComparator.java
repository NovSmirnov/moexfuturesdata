package org.smirnovav.moexFuturesData.calculations.collectors.comparators;


import org.smirnovav.moexFuturesData.calculations.collectors.futures.AverageLiquidity;

import java.util.Comparator;

public class AvLiquidityVolumeComparator implements Comparator<AverageLiquidity> {
    @Override
    public int compare(AverageLiquidity avLiq1, AverageLiquidity avLiq2) {
        long volume1 = avLiq1.getAverageVolumeLiquidity();
        long volume2 = avLiq2.getAverageVolumeLiquidity();
        return Double.compare(volume1, volume2);
    }
}
