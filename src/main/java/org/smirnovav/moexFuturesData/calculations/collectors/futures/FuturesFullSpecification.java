package org.smirnovav.moexFuturesData.calculations.collectors.futures;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "Полная актуальная спецификация фьючерса Московской биржи")
public class FuturesFullSpecification {
    @Schema(description = "Краткий код фьючерса", example = "GZZ5")
    private String secId;
    @Schema(description = "Идентификатор режима торгов", example = "RFUD")
    private String boardId;
    @Schema(description = "Краткое наименование фьючерса", example = "GAZR-12.25")
    private String shortName;
    @Schema(description = "Полное наименование фьючерса", example = "Фьючерсный контракт SBRF-6.25")
    private String secName;
    @Schema(description = "Количество знаков после запятой")
    private int decimals;
    @Schema(description = "Минимальный шаг цены")
    private double minStep;
    @Schema(description = "Цена минимального шага цены в рублях")
    private double stepPrice;
    @Schema(description = "Английское наименование фьючерса", example = "GAZR-12.25")
    private String latName;
    @Schema(description = "База краткого кода", example = "GZ")
    private String secType;
    @Schema(description = "Последняя цена")
    private double prevPrice;
    @Schema(description = "Последний день торгов фьючерса")
    private Calendar lastTradeDate;
    @Schema(description = "Дата исполнения фьючерса")
    private Calendar lastDelDate;
    @Schema(description = "Расчётная цена предыдущего дня")
    private double prevSettlePrice;
    @Schema(description = "База краткого наименования контракта", example = "GAZR")
    private String assetCode;

    @Schema(description = "Количество открытых позиций")
    private int prevOpenPosition;
    @Schema(description = "Лот (Единиц базового актива)")
    private int lotVolume;
    @Schema(description = "Гарантийное обеспечение")
    private double initialMargin;
    @Schema(description = "Верхний лимит")
    private double highLimit;
    @Schema(description = "Нижний лимит")
    private double lowLimit;
    @Schema(description = "Расчётная цена последнего клиринга")
    private double lastSettlePrice;
    @Schema(description = "Время и дата последней цены")
    private Calendar imTime;
    @Schema(description = "Сбор за регистрацию сделки")
    private double buySellFee;
    @Schema(description = "Сбор за скальперскую сделку")
    private double scalperFee;
    @Schema(description = "Сбор за адресную сделку")
    private double negotiatedFee;
    @Schema(description = "Клиринговая комиссия за исполнение контракта")
    private double exerciseFee;


    @Schema(description = "Спрос")
    private double bid;
    @Schema(description = "Предложение")
    private double offer;
    @Schema(description = "Объем заявок на продажу по лучшей котировке, выраженный в лотах")
    private double offerDepth;
    @Schema(description = "Объем всех заявок на продажу в очереди торговой системы, выраженный в лотах")
    private double offerDeptht;
    @Schema(description = "Разница между ценой покупки и ценой продажи")
    private double spread;
    @Schema(description = "Цена открытия")
    private double open;
    @Schema(description = "Минимальная цена")
    private double low;
    @Schema(description = "Максимальная цена")
    private double high;
    @Schema(description = "Последняя цена")
    private double last;
    @Schema(description = "Изменение цены последней сделки по отношению к цене последней сделки предыдущего дня")
    private double lastChange;
    @Schema(description = "Количество сделок за торговый день")
    private int numTrades;
    @Schema(description = "Объем сделок за сегодня в количестве ценных бумаг")
    private int volToday;
    @Schema(description = "Объем сделок за сегодня в валюте расчетов")
    private double valToday;
    @Schema(description = "Объем сделок за сегодня в долларах США")
    private double valTodayUsd;
    @Schema(description = "Изменение цены последней сделки по отношению к цене последней сделки предыдущего дня в процентах")
    private double lastChangePrcnt;
    @Schema(description = "Объем заявок на покупку по лучшей котировке, выраженный в лотах")
    private int bidDepth;
    @Schema(description = "Объем всех заявок на покупку в очереди торговой системы, выраженный в лотах")
    private int bidDeptht;
    @Schema(description = "Количество заявок на продажу в очереди торговой системы")
    private int numOffers;
    @Schema(description = "Количество заявок на покупку в очереди торговой системы")
    private int numBids;
    @Schema(description = "Время заключения последней сделки")
    private Calendar time;
    @Schema(description = "14-значное число обозначающее дату и время последней сделки в формате yyyyMMddhhmmss")
    private String seqNum;
    @Schema(description = "Системное время")
    private Calendar sysTime;
    @Schema(description = "Время обновления")
    private Calendar updateTime;
    @Schema(description = "Цена предторгового периода или аукциона открытия")
    private double openPeriodPrice;
    @Schema(description = "???")
    private double lastToPrevPrice;


    @Schema(description = "Объем выраженный в лотах")
    private int quantity;
    @Schema(description = "Расчетная цена")
    private double settlePrice;
    @Schema(description = "Разница текущей установленной цены к предыдущей")
    private double settleToPrevSettle;
    @Schema(description = "Количество открытых позиций")
    private int openPosition;
    @Schema(description = "Разница текущей установленной цены к предыдущей в процентах")
    private double settleToPrevSettlePrc;
    @Schema(description = "Дата сделки")
    private Calendar tradeDate;
    @Schema(description = "???")
    private double oiChange;
    @Schema(description = "???")
    private double swapRate;






}
