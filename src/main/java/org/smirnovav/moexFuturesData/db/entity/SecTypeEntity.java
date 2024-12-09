package org.smirnovav.moexFuturesData.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "sectype")
public class SecTypeEntity {
    @Id
    @Column(name = "sectype")
    private String secType ;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shortName")
    private Set<FuturesEntity> futuresEntities;

    public void addFuturesEntity(FuturesEntity futuresEntity) {
        if (futuresEntities == null) {
            futuresEntities = Set.of(futuresEntity);
        } else {
            futuresEntities.add(futuresEntity);
        }
    }


}
