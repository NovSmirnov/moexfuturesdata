package org.smirnovav.moexFuturesData.db.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class IntegratedFuturesInfoEntity {
    private AssetCodeEntity assetCodeEntity;
    private BoardIdEntity boardIdEntity;
    private DecimalsEntity decimalsEntity;
    private List<FuturesDayHistoryEntity> futuresDayHistoryEntityList;
    private FuturesEntity futuresEntity;
    private FuturesMarketDataEntity futuresMarketDataEntity;
    private MinStepEntity minStepEntity;
    private SecTypeEntity secTypeEntity;
}
