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
    @JoinColumn(name = "boardname", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BoardIdEntity boardIdEntity; // Board на бирже
    @Id
    @Column(name = "shortname")
    private String shortName; // Краткое наименование
    @Column(name = "secname")
    private String secName; //Полное имя бумаги
    @JoinColumn(name = "decimals", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private DecimalsEntity decimalsEntity; //Количество знаков после запятой
    @JoinColumn(name = "minstep", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private MinStepEntity minStepEntity; //Шаг цены
    @Column(name = "latname")
    private String latName; // Английское имя бумаги
    @JoinColumn(name = "sectype", nullable = false)
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
    @JoinColumn(name = "assetcode", nullable = false)
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
    private double stepPrice; //Шаг цены
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




}
