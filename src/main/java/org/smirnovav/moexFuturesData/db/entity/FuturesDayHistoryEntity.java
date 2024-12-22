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
@Table(name = "futuresdayhistory")
public class FuturesDayHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "futuresdayhistoryid")
    private long futuresDayHistoryId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "shortname", nullable = true)
    private FuturesEntity futuresEntity;

//    private String boardId; // Режим торгов
    @Column(name = "tradedate")
    private Calendar tradeDate; //Дата проведения торгов
//    private String secId; // Краткий код

    @Column(name = "open")
    private double open; //Цена открытия
    @Column(name = "low")
    private double low; // Наименьшая цена
    @Column(name = "high")
    private double high; // Наибольшая цена
    @Column(name = "close")
    private double close; // Цена закрытия
    @Column(name = "openpositionvalue")
    private double openPositionValue; // Стоимость всех открытых позиций в рублях
    @Column(name = "value")
    private double value; // Объём торгов в рублях
    @Column(name = "volume")
    private long volume; // Количество сделок
    @Column(name = "openposition")
    private long openPosition; // Количество открытых позиций
    @Column(name = "settleprice")
    private double settlePrice; // Расчётная цена
    @Column(name = "swaprate")
    private double swapRate; // ??
    @Column(name = "waprice")
    private double waPrice; // Средневзвешенная цена
    @Column(name = "settlepriceday")
    private double settlePriceDay;
    @Column(name = "change")
    private double change; // Изменение цены последней сделки по отношению к цене последней сделки предыдущего торгового дня
    @Column(name = "qty")
    private double qty; // Объем последней сделки в лотах
    @Column(name = "numtrades")
    private double numTrades; // Количество сделок за торговый день


}
