package org.smirnovav.moexFuturesData.utils;

import org.smirnovav.moexFuturesData.dto.IntegratedFuturesInfoDto;
import org.smirnovav.moex_lib.collectors.futures.FuturesComplexInfo;
import org.smirnovav.moex_lib.collectors.futures.FuturesDayHistoryInfo;
import org.smirnovav.moex_lib.collectors.stock.StockComplexInfo;
import org.smirnovav.moex_lib.utils.SecurityUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class MoexUtils {

    private MoexUtils() {
    }


    public static List<IntegratedFuturesInfoDto> getAllTradingFuturesInfo(Calendar historyStartDate, Calendar historyFinishDate) {

        List<IntegratedFuturesInfoDto> integratedFuturesInfoDtos = new ArrayList<>();
        Set<String> secIds = SecurityUtils.getAllTradingFutures();
        for (String secId : secIds) {

            integratedFuturesInfoDtos.add(getIntegratedFuturesInfo(secId, historyStartDate, historyFinishDate));
        }
        return integratedFuturesInfoDtos;
    }

    public static IntegratedFuturesInfoDto getIntegratedFuturesInfo(String secID, Calendar historyStartDate, Calendar historyFinishDate) {
        FuturesComplexInfo futuresComplexInfo = SecurityUtils.getFuturesComplexInfo(secID);
        List<FuturesDayHistoryInfo> futuresDayHistoryInfoList = SecurityUtils.getFuturesDailyHistory(secID, historyStartDate, historyFinishDate);
        return new IntegratedFuturesInfoDto(futuresComplexInfo, futuresDayHistoryInfoList);
    }

}
