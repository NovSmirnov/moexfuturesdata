package org.smirnovav.moexFuturesData.reports.excel.one;

import java.math.BigDecimal;

public class RowData {
    private String boardName; // Площадка
    private String ticker; //Инструмент
    private double lastPrice; // Последняя цена (котировка)
    private BigDecimal price; // Полная цена контракта, руб
    private BigDecimal priceStep; //Шаг цены
    private BigDecimal priceStepCost; //Стоимость шага цены
    private BigDecimal initialMargin; // Гарантийное обеспечение
    private int volume; // Среднедневное количество проторгованых лотов за n торговых дней
    private BigDecimal value; // Среднедневной объем сделок за последние n торговых дней, млн. руб
    private double leverage; // Кредитное плечо вшитое во фьючерс
    private double volatility; // Среднедневная волотильность (ATR %) с усреднением за n торговых дней
    private double volatilityLeverage; // Плечо с учетом среднедневной волатильности
    private BigDecimal buySellFeeRur; // Комиссия биржи (видимо за сделку) в рублях
    private BigDecimal buySellFeePer; // Комиссия биржи (видимо за сделку) в процентах от стоимости контракта.
    private BigDecimal brokersFeeRur; // Комиссия брокера за сделку в рублях
    private BigDecimal brokersFeePer; // Комиссия брокера за сделку в процентах к стоимости контракта

    private BigDecimal fullFee; // Общая комиссия в рублях

    private BigDecimal feeToInitialMargin; // Общая комиссия как процент к полной стоимости контракта


    private BigDecimal onePercChPrice; // Один процент изменения цены в рублях
    private int numOfLots; // Количество лотов
    private BigDecimal stopLoss; // Стоп-лосс при заданном проценте риска
    private BigDecimal takeProfit; // Тейк-профит при заданном проценте риска
    private BigDecimal rubRisk; // Риск при заданном проценте риска в рублях
    private BigDecimal perRisk; // Риск при заданном проценте риска в процентах
    private BigDecimal rubProfit; // Потенциальная прибыль при заданном проценте риска в рублях
    private BigDecimal perProfit; // Потенциальная прибыль при заданном проценте риска в процентах
    private BigDecimal feePercentToProfit; // Процент комиссии к потенциальной прибыли
    private BigDecimal profToRisk; // Отношение потенциальной прибыли к риску
    private BigDecimal algoFee; // Комиссия в единицах цены котировки c проскальзыванием в 1 пункт
    private String time; //Дата и время последнего обновления
//    private static BigDecimal accountVolume; // Объём счёта
    private int decimals; //Количество знаков после запятой


}
