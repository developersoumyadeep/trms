package com.wbsedcl.trms.substation.log.dataaccess.feeder.entity;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "feeder_loading_history", schema = "jpa_test")
public class FeederLoadingHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historyId;
    @OneToOne
    @JoinColumn(name = "feeder_id")
    private FeederEntity feederEntity;
    private LocalDate loadedFromDate;
    private LocalTime loadedFromTime;
    private LocalDate loadedToDate;
    private LocalTime loadedToTime;
}
