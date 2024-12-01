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
@Table(name = "boards")
public class BoardIdEntity {

    @Id
    @Column(name = "boardname")
    private String boardName;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shortName")
    private Set<FuturesEntity> futuresEntities;

}
