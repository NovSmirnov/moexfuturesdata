package org.smirnovav.moexFuturesData.mapper;

import org.smirnovav.moexFuturesData.calculations.collectors.futures.FuturesFullSpecification;
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

    public static IntegratedFuturesInfoDto integratedInfoEntityToDto(IntegratedFuturesInfoEntity integratedFuturesInfoEntity) {
        FuturesEntity futuresEntity = integratedFuturesInfoEntity.getFuturesEntity();
        FuturesMarketDataEntity futuresMarketDataEntity = integratedFuturesInfoEntity.getFuturesMarketDataEntity();
        List<FuturesDayHistoryEntity> futuresDayHistoryEntityList = integratedFuturesInfoEntity.getFuturesDayHistoryEntityList();

        FuturesBoardSpecification specification = new FuturesBoardSpecification(futuresEntity.getSecId(),
                futuresEntity.getBoardIdEntity().getBoardName(), futuresEntity.getShortName(),
                futuresEntity.getSecName(), futuresEntity.getDecimalsEntity().getDecimals(),
                futuresEntity.getMinStepEntity().getMinStep(), futuresEntity.getLatName(),
                futuresEntity.getSecTypeEntity().getSecType(), futuresEntity.getPrevPrice(),
                futuresEntity.getPrevSettlePrice(), futuresEntity.getLastTradeDate(), futuresEntity.getLastDelDate(),
                futuresEntity.getAssetCodeEntity().getAssetCode(), futuresEntity.getPrevOpenPosition(),
                futuresEntity.getLotVolume(), futuresEntity.getInitialMargin(), futuresEntity.getHighLimit(),
                futuresEntity.getLowLimit(), futuresEntity.getStepPrice(), futuresEntity.getLastSettlePrice(),
                futuresEntity.getImTime(), futuresEntity.getBuySellFee(), futuresEntity.getScalperFee(),
                futuresEntity.getNegotiatedFee(), futuresEntity.getExerciseFee());

        FuturesMarketData futuresMarketData = new FuturesMarketData(futuresEntity.getSecId(),
                futuresEntity.getBoardIdEntity().getBoardName(), futuresMarketDataEntity.getBid(),
                futuresMarketDataEntity.getOffer(), futuresMarketDataEntity.getOfferDepth(),
                futuresMarketDataEntity.getOfferDeptht(), futuresMarketDataEntity.getSpread(),
                futuresMarketDataEntity.getOpen(), futuresMarketDataEntity.getLow(), futuresMarketDataEntity.getHigh(),
                futuresMarketDataEntity.getLast(), futuresMarketDataEntity.getLastChange(),
                futuresMarketDataEntity.getNumTrades(), futuresMarketDataEntity.getVolToday(),
                futuresMarketDataEntity.getValToday(), futuresMarketDataEntity.getValTodayUsd(),
                futuresMarketDataEntity.getLastChangePrcnt(), futuresMarketDataEntity.getBidDepth(),
                futuresMarketDataEntity.getBidDeptht(), futuresMarketDataEntity.getNumOffers(),
                futuresMarketDataEntity.getNumBids(), futuresMarketDataEntity.getTime(),
                futuresMarketDataEntity.getSeqNum(), futuresMarketDataEntity.getSysTime(),
                futuresMarketDataEntity.getUpdateTime(), futuresMarketDataEntity.getOpenPeriodPrice(),
                futuresMarketDataEntity.getLastToPrevPrice(), futuresMarketDataEntity.getQuantity(),
                futuresMarketDataEntity.getSettlePrice(), futuresMarketDataEntity.getSettleToPrevSettle(),
                futuresMarketDataEntity.getOpenPosition(), futuresMarketDataEntity.getSettleToPrevSettlePrc(),
                futuresMarketDataEntity.getTradeDate(), futuresMarketDataEntity.getOiChange(),
                futuresMarketDataEntity.getSwapRate());

        List<FuturesDayHistoryInfo> futuresDayHistoryInfoList = new ArrayList<>();
        for (FuturesDayHistoryEntity entity : futuresDayHistoryEntityList) {
            futuresDayHistoryInfoList.add(new FuturesDayHistoryInfo(futuresEntity.getBoardIdEntity().getBoardName(),
                    entity.getTradeDate(), futuresEntity.getSecId(), entity.getOpen(), entity.getLow(),
                    entity.getHigh(), entity.getClose(), entity.getOpenPositionValue(), entity.getValue(),
                    entity.getVolume(), entity.getOpenPosition(), entity.getSettlePrice(), entity.getSwapRate(),
                    entity.getWaPrice(), entity.getSettlePriceDay(), entity.getChange(), entity.getQty(),
                    entity.getNumTrades()));
        }

        FuturesComplexInfo futuresComplexInfo = new FuturesComplexInfo(specification, futuresMarketData);
        return new IntegratedFuturesInfoDto(futuresComplexInfo, futuresDayHistoryInfoList);
    }


    public static List<IntegratedFuturesInfoEntity> integratedInfoDtoListToEntities(List<IntegratedFuturesInfoDto> integratedFuturesInfoDtos) {
        List<IntegratedFuturesInfoEntity> integratedFuturesInfoEntities = new ArrayList<>();
        for (IntegratedFuturesInfoDto integratedFuturesInfoDto : integratedFuturesInfoDtos) {
            integratedFuturesInfoEntities.add(integratedInfoDtoToEntity(integratedFuturesInfoDto));
        }
        return integratedFuturesInfoEntities;
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

    public static FuturesFullSpecification integratedEntityToFullSpecification(IntegratedFuturesInfoEntity integratedFuturesInfoEntity) {
        FuturesEntity futuresEntity = integratedFuturesInfoEntity.getFuturesEntity();
        FuturesMarketDataEntity futuresMarketDataEntity = integratedFuturesInfoEntity.getFuturesMarketDataEntity();
        return FuturesFullSpecification.builder()
                .secId(futuresEntity.getSecId())
                .boardId(integratedFuturesInfoEntity.getBoardIdEntity().getBoardName())
                .shortName(futuresEntity.getShortName())
                .secName(futuresEntity.getSecName())
                .decimals(integratedFuturesInfoEntity.getDecimalsEntity().getDecimals())
                .minStep(integratedFuturesInfoEntity.getMinStepEntity().getMinStep())
                .stepPrice(futuresEntity.getStepPrice())
                .latName(futuresEntity.getLatName())
                .secType(integratedFuturesInfoEntity.getSecTypeEntity().getSecType())
                .prevPrice(futuresEntity.getPrevPrice())
                .lastTradeDate(futuresEntity.getLastTradeDate())
                .lastDelDate(futuresEntity.getLastDelDate())
                .prevSettlePrice(futuresEntity.getPrevSettlePrice())
                .assetCode(integratedFuturesInfoEntity.getAssetCodeEntity().getAssetCode())
                .prevOpenPosition(futuresEntity.getPrevOpenPosition())
                .lotVolume(futuresEntity.getLotVolume())
                .initialMargin(futuresEntity.getInitialMargin())
                .highLimit(futuresEntity.getHighLimit())
                .lowLimit(futuresEntity.getLowLimit())
                .lastSettlePrice(futuresEntity.getLastSettlePrice())
                .imTime(futuresEntity.getImTime())
                .buySellFee(futuresEntity.getBuySellFee())
                .scalperFee(futuresEntity.getScalperFee())
                .negotiatedFee(futuresEntity.getNegotiatedFee())
                .exerciseFee(futuresEntity.getExerciseFee())
                .bid(futuresMarketDataEntity.getBid())
                .offer(futuresMarketDataEntity.getOffer())
                .offerDepth(futuresMarketDataEntity.getOfferDepth())
                .offerDeptht(futuresMarketDataEntity.getOfferDeptht())
                .spread(futuresMarketDataEntity.getSpread())
                .open(futuresMarketDataEntity.getOpen())
                .low(futuresMarketDataEntity.getLow())
                .high(futuresMarketDataEntity.getHigh())
                .last(futuresMarketDataEntity.getLast())
                .lastChange(futuresMarketDataEntity.getLastChange())
                .numTrades(futuresMarketDataEntity.getNumTrades())
                .volToday(futuresMarketDataEntity.getVolToday())
                .valToday(futuresMarketDataEntity.getValToday())
                .valTodayUsd(futuresMarketDataEntity.getValTodayUsd())
                .lastChangePrcnt(futuresMarketDataEntity.getLastChangePrcnt())
                .bidDepth(futuresMarketDataEntity.getBidDepth())
                .bidDeptht(futuresMarketDataEntity.getBidDeptht())
                .numOffers(futuresMarketDataEntity.getNumOffers())
                .numBids(futuresMarketDataEntity.getNumBids())
                .time(futuresMarketDataEntity.getTime())
                .seqNum(futuresMarketDataEntity.getSeqNum())
                .sysTime(futuresMarketDataEntity.getSysTime())
                .updateTime(futuresMarketDataEntity.getUpdateTime())
                .openPeriodPrice(futuresMarketDataEntity.getOpenPeriodPrice())
                .lastToPrevPrice(futuresMarketDataEntity.getLastToPrevPrice())
                .quantity(futuresMarketDataEntity.getQuantity())
                .settlePrice(futuresMarketDataEntity.getSettlePrice())
                .settleToPrevSettle(futuresMarketDataEntity.getSettleToPrevSettle())
                .openPosition(futuresMarketDataEntity.getOpenPosition())
                .settleToPrevSettlePrc(futuresMarketDataEntity.getSettleToPrevSettlePrc())
                .tradeDate(futuresMarketDataEntity.getTradeDate())
                .oiChange(futuresMarketDataEntity.getOiChange())
                .swapRate(futuresMarketDataEntity.getSwapRate())
                .build();
    }

}
