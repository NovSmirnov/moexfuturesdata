package org.smirnovav.moexFuturesData.calculations.collectors.comparators;

import org.smirnovav.moexFuturesData.calculations.collectors.futures.VolatilityLiquidity;

import java.util.Comparator;

public class VolatilityLeverageComparator implements Comparator<VolatilityLiquidity> {
    @Override
    public int compare(VolatilityLiquidity volLiq1, VolatilityLiquidity volLiq2) {
        double atr1;
        double atr2;
        if (volLiq1.getVolatilityChartData().getAtrLeverageValues().isEmpty() ||
                volLiq1.getVolatilityChartData().getAtrLeverageValues() == null) {
            atr1 = 0;
        } else {
            atr1 = volLiq1.getVolatilityChartData().getAtrLeverageValues().
                    get(volLiq1.getVolatilityChartData().getAtrLeverageValues().size() - 1);
        }

        if(volLiq2.getVolatilityChartData().getAtrLeverageValues().isEmpty() ||
                volLiq2.getVolatilityChartData().getAtrLeverageValues() == null) {
            atr2 = 0;
        } else {
            atr2 = volLiq2.getVolatilityChartData().getAtrLeverageValues().
                    get(volLiq2.getVolatilityChartData().getAtrLeverageValues().size() - 1);
        }
        return Double.compare(atr1, atr2);
    }

}
