package com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity;

import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyUnit;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "energy_consumption_master", schema = "substation_log_schema")
@Entity
public class EnergyConsumptionEntity {
    @Id
    private String consumptionId;
    @OneToOne
    @JoinColumn(name="feeder_id")
    private FeederEntity feeder;
    @OneToOne
    @JoinColumn(name="substation_office_id")
    private OfficeEntity substationOffice;
    private LocalDate recordDate;
    private LocalTime recordTime;
    private String energyMeterNo;
    @Enumerated(EnumType.STRING)
    private EnergyUnit energyUnit;
    private double consumption;
    private int multiplyingFactor;
    @OneToOne
    @JoinColumn(name="recorded_by")
    private UserEntity recordedBy;
}
