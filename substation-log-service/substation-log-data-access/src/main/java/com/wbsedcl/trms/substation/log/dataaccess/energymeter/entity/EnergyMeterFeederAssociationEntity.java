package com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity;

import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ss_log_energy_meter_feeder_association", schema = "substation_log_schema")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnergyMeterFeederAssociationEntity {
    @Id
    @Column(name = "association_id")
    private String associationId;
    @ManyToOne
    @JoinColumn(name = "energy_meter_no")
    private EnergyMeterEntity energyMeter;
    @ManyToOne
    @JoinColumn(name = "feeder_id")
    private FeederEntity feeder;
    @Column(name = "association_start_date")
    private LocalDate startDate;
    @Column(name = "association_start_time")
    private LocalTime startTime;
    @Column(name = "association_end_date")
    private LocalDate endDate;
    @Column(name = "association_end_time")
    private LocalTime endTime;
}
