package com.wbsedcl.trms.substation.log.dataaccess.feeder.entity;

import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.domain.entity.FeederType;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ss_log_feeder_m_view", schema = "substation_log_schema")
@Entity
public class FeederEntity {
    @Id
    @Column(name = "feeder_id")
    private String feederId;
    private String feederName;
    private String energyMeterNo;
    @OneToOne
    @JoinColumn(name = "substation_office_id")
    private OfficeEntity substationOffice;
    private Integer voltageLevel;
    @Enumerated(EnumType.STRING)
    private FeederType feederType;

    @ManyToOne
    @JoinColumn(name = "incomer_11kV_feeder_id")
    private FeederEntity incomer11kVFeeder;
    @ManyToOne
    @JoinColumn(name = "feeding_ptr_id")
    private FeederEntity feedingPTR;
    @ManyToOne
    @JoinColumn(name = "feeding_33kV_feeder_id")
    private FeederEntity feeding33kVFeeder;
    private Boolean isCharged;
    private Boolean isLoaded;
}
