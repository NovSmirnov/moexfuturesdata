package org.smirnovav.moexFuturesData.db.repository;

import org.smirnovav.moexFuturesData.db.entity.FuturesMarketDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuturesMarketDataRepository extends JpaRepository<FuturesMarketDataEntity, Long> {
}
