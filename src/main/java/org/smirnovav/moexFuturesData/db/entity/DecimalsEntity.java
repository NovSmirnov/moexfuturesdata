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
@Table(name = "decimals")
public class DecimalsEntity {

    @Id
    @Column(name = "decimals")
    private int decimals;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shortName")
    private Set<FuturesEntity> futuresEntities;

}
