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
@Table(name = "minstep")
public class MinStepEntity {

    @Id
    @Column(name = "minstep")
    private double minStep ;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shortName")
    private Set<FuturesEntity> futuresEntities;

}
