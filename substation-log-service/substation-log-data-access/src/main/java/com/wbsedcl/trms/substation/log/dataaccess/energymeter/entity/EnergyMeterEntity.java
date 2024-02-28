package com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity;

import com.wbsedcl.trms.substation.log.domain.entity.EnergyUnit;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ss_log_energy_meter_master", schema = "substation_log_schema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnergyMeterEntity {
    @Id
    @Column(name = "energy_meter_no")
    private String energyMeterNo;
    @Column(name = "meter_ct_ratio")
    private double meterCTRatio;
    @Column(name = "meter_pt_ratio")
    private double meterPTRatio;
    @Column(name = "energy_unit")
    @Enumerated(EnumType.STRING)
    private EnergyUnit energyUnit;
}
