package com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity;

import com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity.EnergyMeterEntity;
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
@Table(name = "ss_log_energy_meter_reading_master", schema = "substation_log_schema")
@Entity
@ToString
public class EnergyMeterReadingEntity {
    @Id
    private String meterReadingId;
    @OneToOne
    @JoinColumn(name="feeder_id")
    private FeederEntity feeder;
    @OneToOne
    @JoinColumn(name="substation_office_id")
    private OfficeEntity substationOffice;
    private LocalDate recordDate;
    private LocalTime recordTime;
    @OneToOne
    @JoinColumn(name="energy_meter_no")
    private EnergyMeterEntity energyMeter;
    @Enumerated(EnumType.STRING)
    private EnergyUnit energyUnit;
    @Column(name = "meter_reading")
    private Double meterReading;
    @OneToOne
    @JoinColumn(name="recorded_by")
    private UserEntity recordedBy;
    @Column(name = "multiplying_factor")
    private Double mf;

    @PreUpdate
    @PrePersist
    public void calculateMf() {
        mf = (feeder.getInstalledCTRatio()* feeder.getInstalledPTRatio())/(energyMeter.getMeterCTRatio()*energyMeter.getMeterPTRatio());
    }
}
