package org.smirnovav.moexFuturesData.db.repository;

import org.smirnovav.moexFuturesData.db.entity.BoardIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardIdRepository extends JpaRepository<BoardIdEntity, String> {

}
