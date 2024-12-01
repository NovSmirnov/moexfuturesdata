package org.smirnovav.moexFuturesData.db.repository;

import org.smirnovav.moexFuturesData.db.entity.MinStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinStepRepository extends JpaRepository<MinStepEntity, Double> {
}
