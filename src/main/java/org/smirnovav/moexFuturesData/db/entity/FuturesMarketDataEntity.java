package org.smirnovav.moexFuturesData.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "futuresmarketdata")
public class FuturesMarketDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "futuresmarketdataid")
    private long futuresMarketDataId;
//    @JoinColumn(name = "shortname")
    @OneToOne(fetch = FetchType.EAGER)
    private FuturesEntity futuresEntity;
    @Column(name = "bid")
    private double bid; // Спрос
    @Column(name = "offer")
    private double offer; // Предложение
    @Column(name = "offerdepth")
    private double offerDepth; // Объем заявок на продажу по лучшей котировке, выраженный в лотах
    @Column(name = "offerdeptht")
    private double offerDeptht; // Объем всех заявок на продажу в очереди торговой системы, выраженный в лотах
    @Column(name = "spread")
    private double spread; // Разница между ценой покупки и ценой продажи
    @Column(name = "open")
    private double open; // Цена открытия
    @Column(name = "low")
    private double low; // Минимальная цена
    @Column(name = "high")
    private double high; // Максимальная цена
    @Column(name = "last")
    private double last; // Последняя цена
    @Column(name = "lastchange")
    private double lastChange; // Изменение цены последней сделки по отношению к цене последней сделки предыдущего дня
    @Column(name = "numtrades")
    private int numTrades; // Количество сделок за торговый день
    @Column(name = "voltoday")
    private int volToday; // Объем сделок за сегодня в количестве ценных бумаг
    @Column(name = "valtoday")
    private double valToday; // Объем сделок за сегодня в валюте расчетов
    @Column(name = "valtodayusd")
    private double valTodayUsd; // Объем сделок за сегодня в долларах США
    @Column(name = "lastchangeprcnt")
    private double lastChangePrcnt; // Изменение цены последней сделки по отношению к цене последней сделки предыдущего дня в процентах
    @Column(name = "biddepth")
    private int bidDepth; // Объем заявок на покупку по лучшей котировке, выраженный в лотах
    @Column(name = "biddeptht")
    private int bidDeptht; // Объем всех заявок на покупку в очереди торговой системы, выраженный в лотах
    @Column(name = "numoffers")
    private int numOffers; // Количество заявок на продажу в очереди торговой системы
    @Column(name = "numbids")
    private int numBids; // Количество заявок на покупку в очереди торговой системы
    @Column(name = "time")
    private Calendar time; // Время заключения последней сделки
    @Column(name = "seqnum")
    private String seqNum; // Некий 14-значный номер обозначающий дату и время последней сделки в формате yyyyMMddhhmmss
    @Column(name = "systime")
    private Calendar sysTime; // Системное время
    @Column(name = "updatetime")
    private Calendar updateTime; // Время обновления
    @Column(name = "openperiodprice")
    private double openPeriodPrice; // Цена предторгового периода или аукциона открытия
    @Column(name = "lasttoprevprice")
    private double lastToPrevPrice; // ???
    @Column(name = "quantity")
    private int quantity; // Объем выраженный в лотах
    @Column(name = "settleprice")
    private double settlePrice; // Расчетная цена
    @Column(name = "settletoprevsettle")
    private double settleToPrevSettle; // Разница текущей установленной цены к предыдущей
    @Column(name = "openposition")
    private int openPosition; // Количество открытых позиций
    @Column(name = "settletoprevsettleprc")
    private double settleToPrevSettlePrc; // Разница текущей установленной цены к предыдущей в процентах
    @Column(name = "tradedate")
    private Calendar tradeDate; // Дата сделки
    @Column(name = "oichange")
    private double oiChange; // ???
    @Column(name = "swaprate")
    private double swapRate; // ???

    public void update(FuturesMarketDataEntity futuresMarketDataEntity) {
//        futuresMarketDataId = futuresMarketDataEntity.;
//        futuresEntity = futuresMarketDataEntity.futuresEntity;
        bid = futuresMarketDataEntity.getBid(); // Спрос
        offer = futuresMarketDataEntity.getOffer(); // Предложение
        offerDepth = futuresMarketDataEntity.getOfferDepth(); // Объем заявок на продажу по лучшей котировке, выраженный в лотах
        offerDeptht = futuresMarketDataEntity.getOfferDeptht(); // Объем всех заявок на продажу в очереди торговой системы, выраженный в лотах
        spread = futuresMarketDataEntity.getSpread(); // Разница между ценой покупки и ценой продажи
        open = futuresMarketDataEntity.getOpen(); // Цена открытия
        low = futuresMarketDataEntity.getLow(); // Минимальная цена
        high = futuresMarketDataEntity.getHigh(); // Максимальная цена
        last = futuresMarketDataEntity.getLast(); // Последняя цена
        lastChange = futuresMarketDataEntity.getLastChange(); // Изменение цены последней сделки по отношению к цене последней сделки предыдущего дня
        numTrades = futuresMarketDataEntity.getNumTrades(); // Количество сделок за торговый день
        volToday = futuresMarketDataEntity.getVolToday(); // Объем сделок за сегодня в количестве ценных бумаг
        valToday = futuresMarketDataEntity.getValToday(); // Объем сделок за сегодня в валюте расчетов
        valTodayUsd = futuresMarketDataEntity.getValTodayUsd(); // Объем сделок за сегодня в долларах США
        lastChangePrcnt = futuresMarketDataEntity.getLastChangePrcnt(); // Изменение цены последней сделки по отношению к цене последней сделки предыдущего дня в процентах
        bidDepth = futuresMarketDataEntity.getBidDepth(); // Объем заявок на покупку по лучшей котировке, выраженный в лотах
        bidDeptht = futuresMarketDataEntity.getBidDeptht(); // Объем всех заявок на покупку в очереди торговой системы, выраженный в лотах
        numOffers = futuresMarketDataEntity.getNumOffers(); // Количество заявок на продажу в очереди торговой системы
        numBids = futuresMarketDataEntity.getNumBids(); // Количество заявок на покупку в очереди торговой системы
        time = futuresMarketDataEntity.getTime(); // Время заключения последней сделки
        seqNum = futuresMarketDataEntity.getSeqNum(); // Некий 14-значный номер обозначающий дату и время последней сделки в формате yyyyMMddhhmmss
        sysTime = futuresMarketDataEntity.getSysTime(); // Системное время
        updateTime = futuresMarketDataEntity.getUpdateTime(); // Время обновления
        openPeriodPrice = futuresMarketDataEntity.getOpenPeriodPrice(); // Цена предторгового периода или аукциона открытия
        lastToPrevPrice = futuresMarketDataEntity.getLastToPrevPrice(); // ???
        quantity = futuresMarketDataEntity.getQuantity(); // Объем выраженный в лотах
        settlePrice = futuresMarketDataEntity.getSettlePrice(); // Расчетная цена
        settleToPrevSettle = futuresMarketDataEntity.getSettleToPrevSettle(); // Разница текущей установленной цены к предыдущей
        openPosition = futuresMarketDataEntity.getOpenPosition(); // Количество открытых позиций
        settleToPrevSettlePrc = futuresMarketDataEntity.getSettleToPrevSettlePrc(); // Разница текущей установленной цены к предыдущей в процентах
        tradeDate = futuresMarketDataEntity.getTradeDate(); // Дата сделки
        oiChange = futuresMarketDataEntity.getOiChange(); // ???
        swapRate = futuresMarketDataEntity.getSwapRate(); // ???
    }




}
