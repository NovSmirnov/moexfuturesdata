package org.smirnovav.moexFuturesData.calculations.collectors.futures;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Calendar;
import java.util.List;

@Getter
@Schema(description = "Данные о среднедневной ликвидности актива в лотах и рублях и о истории среднедневной" +
        "волатильности, выраженной в качестве индикатора ATR в процентах")
public class VolatilityLiquidity extends AverageLiquidity{

    @Schema(description = "Период в днях, применяемый для расчета индикатора ATR")
    private int atrPeriod;
    @Schema(description = "данные для построения графика")
    private VolatilityChartData volatilityChartData;

    public VolatilityLiquidity(long averageVolumeLiquidity, long averageValueLiquidity, int averagingPeriodInDays,
                               String secId, String shortName, Calendar lastDate, int atrPeriod,
                              List<Double> atrValues, List<Double> atrLeverageValues,  List<Calendar> dates) {
        super(averageVolumeLiquidity, averageValueLiquidity, averagingPeriodInDays, secId, shortName, lastDate);
        this.atrPeriod = atrPeriod;
        this.volatilityChartData = new VolatilityChartData(atrValues, atrLeverageValues, dates);
    }

    @AllArgsConstructor
    @Getter
    @Schema(description = "Исторические данные показателя ATR % для построения графика")
    public class VolatilityChartData {
        @Schema(description = "История значений показателя ATR %")
        private List<Double> atrValues;
        @Schema(description = "История значений показателя ATR % умноженного на последнее значение \"вшитого\" кредитного плеча")
        private List<Double> atrLeverageValues;
        @Schema(description = "Даты, соответствующие по индексу значениям показателя ATR %")
        private List<Calendar> dates;
    }


}
