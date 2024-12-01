package org.smirnovav.moexFuturesData.dto;

import lombok.*;
import org.smirnovav.moex_lib.collectors.futures.FuturesComplexInfo;
import org.smirnovav.moex_lib.collectors.futures.FuturesDayHistoryInfo;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
public class IntegratedFuturesInfoDto {
    private FuturesComplexInfo futuresComplexInfo;
    private List<FuturesDayHistoryInfo> futuresDayHistoryInfoList;
}
