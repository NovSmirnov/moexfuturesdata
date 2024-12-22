package org.smirnovav.moexFuturesData.calculations.collectors.futures;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Calendar;

@AllArgsConstructor
@Getter
@Data
@Schema(description = "Данные о среднедневной ликвидности актива в лотах и рублях")
public class AverageLiquidity {
    @Schema(description = "Среднедневная ликвидность бумаги, выраженная в лотах")
    private long averageVolumeLiquidity;
    @Schema(description = "Среднедневная ликвидность бумаги, выраженная в рублях")
    private long averageValueLiquidity;
    @Schema(description = "Период усреднения в торговых днях")
    private int averagingPeriodInDays;
    @Schema(description = "description = Краткий код фьючерса", example = "GZZ5")
    private String secId;
    @Schema(description = "Краткое наименование фьючерса", example = "GAZR-12.25")
    private String shortName;
    @Schema(description = "Последняя дата, на которую рассчитана ликвидность")
    private Calendar lastDate;
}
