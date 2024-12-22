package org.smirnovav.moexFuturesData.db.repository;

import org.smirnovav.moexFuturesData.db.entity.FuturesDayHistoryEntity;
import org.smirnovav.moexFuturesData.db.entity.FuturesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuturesDayHistoryRepository extends JpaRepository<FuturesDayHistoryEntity, Long> {
    List<FuturesDayHistoryEntity> findAllByFuturesEntity(FuturesEntity futuresEntity);
}
