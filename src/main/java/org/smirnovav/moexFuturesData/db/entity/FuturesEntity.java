package org.smirnovav.moexFuturesData.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "futures")
public class FuturesEntity {
    @Column(name = "secid")
    private String secId; //Краткий код
    @JoinColumn(name = "boardname", nullable = true)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BoardIdEntity boardIdEntity; // Board на бирже
    @Id
    @Column(name = "shortname")
    private String shortName; // Краткое наименование
    @Column(name = "secname")
    private String secName; //Полное имя бумаги
    @JoinColumn(name = "decimals", nullable = true)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private DecimalsEntity decimalsEntity; //Количество знаков после запятой
    @JoinColumn(name = "minstep", nullable = true)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private MinStepEntity minStepEntity; //Шаг цены
    @Column(name = "latname")
    private String latName; // Английское имя бумаги
    @JoinColumn(name = "sectype", nullable = true)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private SecTypeEntity secTypeEntity; //База краткого кода
    @Column(name = "prevprice")
    private double prevPrice; //Последняя цена
    @Column(name = "prevsettleprice")
    private double prevSettlePrice; // Расчётная цена предыдущего дня
    @Column(name = "lasttradedate")
    private Calendar lastTradeDate; //Последний день торгов
    @Column(name = "lastdeldate")
    private Calendar lastDelDate; // Дата исполнения
    @JoinColumn(name = "assetcode", nullable = true)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AssetCodeEntity assetCodeEntity; // База краткого наименования контракта (GAZR)
    @Column(name = "prevopenposition")
    private int prevOpenPosition; // Количество открытых позициций
    @Column(name = "lotvolume")
    private int lotVolume; // Лот (Единиц базового актива)
    @Column(name = "initialmargin")
    private double initialMargin; //Гарантийное обеспечение
    @Column(name = "highlimit")
    private double highLimit; //Верхний лимит
    @Column(name = "lowlimit")
    private double lowLimit; //Нижний лимит
    @Column(name = "stepprice")
    private double stepPrice; //Цена минимального шага цены в рублях
    @Column(name = "lastsettleprice")
    private double lastSettlePrice; //Расчётная цена последнего клиринга
    @Column(name = "imtime")
    private Calendar imTime; //Время и дата последней цены
    @Column(name = "buysellfee")
    private double buySellFee; //Сбор за регистрацию сделки
    @Column(name = "scalperfee")
    private double scalperFee; //Сбор за скальперскую сделку
    @Column(name = "negotiatedfee")
    private double negotiatedFee; //Сбор за адресную сделку
    @Column(name = "exercisefee")
    private double exerciseFee; //Клиринговая комиссия за исполенние контракта
    @OneToOne(mappedBy = "futuresEntity", cascade = CascadeType.ALL, optional = false,
    fetch = FetchType.EAGER)
    private FuturesMarketDataEntity futuresMarketDataEntity;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "futuresDayHistoryId")
    private List<FuturesDayHistoryEntity> futuresDayHistoryEntityList;


    public void update(FuturesEntity futuresEntity) {
        secId = futuresEntity.getSecId(); //Краткий код
        boardIdEntity = futuresEntity.getBoardIdEntity(); // Board на бирже
//        shortName = futuresEntity; // Краткое наименование
        secName = futuresEntity.getSecName(); //Полное имя бумаги
//        decimalsEntity = futuresEntity.getDecimalsEntity(); //Количество знаков после запятой
//        minStepEntity = futuresEntity.getMinStepEntity(); //Шаг цены
        latName = futuresEntity.getLatName(); // Английское имя бумаги
//        secTypeEntity = futuresEntity.getSecTypeEntity(); //База краткого кода
        prevPrice = futuresEntity.getPrevPrice(); //Последняя цена
        prevSettlePrice = futuresEntity.getPrevSettlePrice(); // Расчётная цена предыдущего дня
        lastTradeDate = futuresEntity.getLastTradeDate(); //Последний день торгов
        lastDelDate = futuresEntity.getLastDelDate(); // Дата исполнения
//        assetCodeEntity = futuresEntity.getAssetCodeEntity(); // База краткого наименования контракта (GAZR)
        prevOpenPosition = futuresEntity.getPrevOpenPosition(); // Количество открытых позициций
        lotVolume = futuresEntity.getLotVolume(); // Лот (Единиц базового актива)
        initialMargin = futuresEntity.getInitialMargin(); //Гарантийное обеспечение
        highLimit = futuresEntity.getHighLimit(); //Верхний лимит
        lowLimit = futuresEntity.getLowLimit(); //Нижний лимит
        stepPrice = futuresEntity.getStepPrice(); //Шаг цены
        lastSettlePrice = futuresEntity.getLastSettlePrice(); //Расчётная цена последнего клиринга
        imTime = futuresEntity.getImTime(); //Время и дата последней цены
        buySellFee = futuresEntity.getBuySellFee(); //Сбор за регистрацию сделки
        scalperFee = futuresEntity.getScalperFee(); //Сбор за скальперскую сделку
        negotiatedFee = futuresEntity.getNegotiatedFee(); //Сбор за адресную сделку
        exerciseFee = futuresEntity.getExerciseFee(); //Клиринговая комиссия за исполенние контракта
//        futuresMarketDataEntity = futuresEntity.getFuturesMarketDataEntity();
//        futuresDayHistoryEntityList = futuresEntity.getFuturesDayHistoryEntityList();
    }

}
