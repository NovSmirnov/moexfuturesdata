package org.smirnovav.moexFuturesData.db.repository;

import org.smirnovav.moexFuturesData.db.entity.FuturesDayHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuturesDayHistoryRepository extends JpaRepository<FuturesDayHistoryEntity, Long> {
}
