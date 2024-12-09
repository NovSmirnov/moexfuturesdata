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
@Table(name = "assetcode")
public class AssetCodeEntity {
    @Id
    @Column(name = "assetcode")
    private String assetCode ;

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
