package org.smirnovav.moexFuturesData.mapper;

import org.smirnovav.moexFuturesData.db.entity.*;
import org.smirnovav.moexFuturesData.dto.IntegratedFuturesInfoDto;
import org.smirnovav.moex_lib.collectors.futures.FuturesBoardSpecification;
import org.smirnovav.moex_lib.collectors.futures.FuturesComplexInfo;
import org.smirnovav.moex_lib.collectors.futures.FuturesDayHistoryInfo;
import org.smirnovav.moex_lib.collectors.futures.FuturesMarketData;

import java.util.ArrayList;
import java.util.List;

public class FuturesMapper {

    private FuturesMapper() {
    }

    public static IntegratedFuturesInfoEntity integratedInfoDtoToEntity(IntegratedFuturesInfoDto integratedFuturesInfoDto) {

        FuturesComplexInfo futuresComplexInfo = integratedFuturesInfoDto.getFuturesComplexInfo();
        FuturesBoardSpecification specification = futuresComplexInfo.getSpecification();
        FuturesMarketData marketData = futuresComplexInfo.getMarketData();
        List<FuturesDayHistoryInfo> history = integratedFuturesInfoDto.getFuturesDayHistoryInfoList();
        AssetCodeEntity assetCodeEntity = AssetCodeEntity.builder()
                .assetCode(specification == null ? null : specification.getAssetCode())
                .build();
        BoardIdEntity boardIdEntity = BoardIdEntity.builder()
                .boardName(specification.getBoardId())
                .build();
        DecimalsEntity decimalsEntity = DecimalsEntity.builder()
                .decimals(specification.getDecimals())
                .build();
        MinStepEntity minStepEntity = MinStepEntity.builder()
                .minStep(specification.getMinStep())
                .build();
        SecTypeEntity secTypeEntity = SecTypeEntity.builder()
                .secType(specification.getSecType())
                .build();
        FuturesEntity futuresEntity = specificationToFuturesEntity(specification);
        FuturesMarketDataEntity futuresMarketDataEntity = dtoToFuturesMarketDataEntity(marketData);
        List<FuturesDayHistoryEntity> futuresDayHistoryEntityList = dtoListToFuturesDayHistoryList(history);

//        futuresEntity.setAssetCodeEntity(assetCodeEntity);
//        futuresEntity.setBoardIdEntity(boardIdEntity);
//        futuresEntity.setDecimalsEntity(decimalsEntity);
//        futuresEntity.setFuturesDayHistoryEntityList(futuresDayHistoryEntityList);
//        futuresEntity.setFuturesMarketDataEntity(futuresMarketDataEntity);
//        futuresEntity.setMinStepEntity(minStepEntity);
//        futuresEntity.setSecTypeEntity(secTypeEntity);
//
//        for (FuturesDayHistoryEntity futuresDayHistoryEntity : futuresDayHistoryEntityList) {
//            futuresDayHistoryEntity.setFuturesEntity(futuresEntity);
//        }
//        futuresMarketDataEntity.setFuturesEntity(futuresEntity);
        return IntegratedFuturesInfoEntity.builder()
                .assetCodeEntity(assetCodeEntity)
                .boardIdEntity(boardIdEntity)
                .decimalsEntity(decimalsEntity)
                .futuresDayHistoryEntityList(futuresDayHistoryEntityList)
                .futuresEntity(futuresEntity)
                .futuresMarketDataEntity(futuresMarketDataEntity)
                .minStepEntity(minStepEntity)
                .secTypeEntity(secTypeEntity)
                .build();
    }

    public static FuturesEntity specificationToFuturesEntity(FuturesBoardSpecification futuresBoardSpecification) {
        return FuturesEntity.builder()
                .secId(futuresBoardSpecification.getSecId())
                .shortName(futuresBoardSpecification.getShortName())
                .secName(futuresBoardSpecification.getSecName())
                .latName(futuresBoardSpecification.getLatName())
                .prevPrice(futuresBoardSpecification.getPrevPrice())
                .prevSettlePrice(futuresBoardSpecification.getPrevSettlePrice())
                .lastTradeDate(futuresBoardSpecification.getLastTradeDate())
                .lastDelDate(futuresBoardSpecification.getLastDelDate())
                .prevOpenPosition(futuresBoardSpecification.getPrevOpenPosition())
                .lotVolume(futuresBoardSpecification.getLotVolume())
                .initialMargin(futuresBoardSpecification.getInitialMargin())
                .highLimit(futuresBoardSpecification.getHighLimit())
                .lowLimit(futuresBoardSpecification.getLowLimit())
                .stepPrice(futuresBoardSpecification.getStepPrice())
                .lastSettlePrice(futuresBoardSpecification.getLastSettlePrice())
                .imTime(futuresBoardSpecification.getImTime())
                .buySellFee(futuresBoardSpecification.getBuySellFee())
                .scalperFee(futuresBoardSpecification.getScalperFee())
                .negotiatedFee(futuresBoardSpecification.getNegotiatedFee())
                .exerciseFee(futuresBoardSpecification.getExerciseFee())
                .build();
    }

    public static FuturesMarketDataEntity dtoToFuturesMarketDataEntity(FuturesMarketData futuresMarketData) {
        return FuturesMarketDataEntity.builder()
                .bid(futuresMarketData.getBid())
                .offer(futuresMarketData.getOffer())
                .offerDepth(futuresMarketData.getOfferDepth())
                .offerDeptht(futuresMarketData.getOfferDeptht())
                .spread(futuresMarketData.getSpread())
                .open(futuresMarketData.getOpen())
                .low(futuresMarketData.getLow())
                .high(futuresMarketData.getHigh())
                .last(futuresMarketData.getLast())
                .lastChange(futuresMarketData.getLastChange())
                .numTrades(futuresMarketData.getNumTrades())
                .volToday(futuresMarketData.getVolToday())
                .valToday(futuresMarketData.getValToday())
                .valTodayUsd(futuresMarketData.getValTodayUsd())
                .lastChangePrcnt(futuresMarketData.getLastChangePrcnt())
                .bidDepth(futuresMarketData.getBidDepth())
                .bidDeptht(futuresMarketData.getBidDeptht())
                .numOffers(futuresMarketData.getNumOffers())
                .numBids(futuresMarketData.getNumBids())
                .time(futuresMarketData.getTime())
                .seqNum(futuresMarketData.getSeqNum())
                .sysTime(futuresMarketData.getSysTime())
                .updateTime(futuresMarketData.getUpdateTime())
                .openPeriodPrice(futuresMarketData.getOpenPeriodPrice())
                .lastToPrevPrice(futuresMarketData.getLastToPrevPrice())
                .quantity(futuresMarketData.getQuantity())
                .settlePrice(futuresMarketData.getSettlePrice())
                .settleToPrevSettle(futuresMarketData.getSettleToPrevSettle())
                .openPosition(futuresMarketData.getOpenPosition())
                .settleToPrevSettlePrc(futuresMarketData.getSettleToPrevSettlePrc())
                .tradeDate(futuresMarketData.getTradeDate())
                .oiChange(futuresMarketData.getOiChange())
                .swapRate(futuresMarketData.getSwapRate())
                .build();
    }

    public static FuturesDayHistoryEntity dtoToFuturesDayHistoryEntity(FuturesDayHistoryInfo futuresDayHistoryInfo) {
        return FuturesDayHistoryEntity.builder()
                .tradeDate(futuresDayHistoryInfo.getTradeDate())
                .open(futuresDayHistoryInfo.getOpen())
                .low(futuresDayHistoryInfo.getLow())
                .high(futuresDayHistoryInfo.getHigh())
                .close(futuresDayHistoryInfo.getClose())
                .openPositionValue(futuresDayHistoryInfo.getOpenPositionValue())
                .value(futuresDayHistoryInfo.getValue())
                .volume(futuresDayHistoryInfo.getVolume())
                .openPosition(futuresDayHistoryInfo.getOpenPosition())
                .settlePrice(futuresDayHistoryInfo.getSettlePrice())
                .swapRate(futuresDayHistoryInfo.getSwapRate())
                .waPrice(futuresDayHistoryInfo.getWaPrice())
                .settlePriceDay(futuresDayHistoryInfo.getSettlePriceDay())
                .change(futuresDayHistoryInfo.getChange())
                .qty(futuresDayHistoryInfo.getQty())
                .numTrades(futuresDayHistoryInfo.getNumTrades())
                .build();
    }

    public static List<FuturesDayHistoryEntity> dtoListToFuturesDayHistoryList(List<FuturesDayHistoryInfo> futuresDayHistoryInfoList) {
        List<FuturesDayHistoryEntity> futuresDayHistoryEntityList = new ArrayList<>();
        for (FuturesDayHistoryInfo futuresDayHistoryInfo : futuresDayHistoryInfoList) {
            futuresDayHistoryEntityList.add(dtoToFuturesDayHistoryEntity(futuresDayHistoryInfo));
        }
        return futuresDayHistoryEntityList;
    }

}
