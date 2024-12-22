package org.smirnovav.moexFuturesData.db.repository;

import org.smirnovav.moexFuturesData.db.entity.DecimalsEntity;
import org.smirnovav.moexFuturesData.db.entity.FuturesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecimalsRepository extends JpaRepository<DecimalsEntity, Integer> {
}
