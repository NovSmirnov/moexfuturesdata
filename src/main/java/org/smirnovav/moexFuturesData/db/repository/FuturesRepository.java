package org.smirnovav.moexFuturesData.db.repository;

import org.smirnovav.moexFuturesData.db.entity.AssetCodeEntity;
import org.smirnovav.moexFuturesData.db.entity.FuturesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface FuturesRepository extends JpaRepository<FuturesEntity, String> {
    List<FuturesEntity> findAllByLastTradeDateBefore(Calendar day);
    @Query(value = "SELECT shortName FROM FuturesEntity" )
    List<String> findAllIds();

//    @Query(value = "SELECT shortName FROM FuturesEntity WHERE assetCode = assetCodeEntity.getAssetCode")
    List<FuturesEntity> findAllByAssetCodeEntity(AssetCodeEntity assetCodeEntity);

}
