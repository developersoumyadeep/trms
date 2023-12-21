package com.wbsedcl.trms.feeder.management.dataaccess.feeder.entity;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.feeder.management.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.feeder.management.domain.entity.FeederType;
import javax.persistence.*;
import lombok.*;

import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feeder_master", schema = "feeder_management_schema")
@Entity
public class FeederEntity {
    @Id
    @Column(name = "feeder_id")
    private String feederId;
    private String feederText;
    private Integer voltageLevel;
    @OneToOne
    @JoinColumn(name = "source_substation_office_id")
    private OfficeEntity sourceSubstationOffice;
    @OneToOne(optional = true)
    @JoinColumn(name= "terminal_substation_office_id")
    private OfficeEntity terminalSubstationOffice;
    private String energyMeterNoAtSource;
    private String energyMeterNoAtTerminal;
    private Double gisLength;
    private Double ctRatioAtSource;
    private Double ctRatioAtTerminal;
    private Double multiplyingFactor;
    @Enumerated(EnumType.STRING)
    @Column(name = "feeder_type", nullable = false)
    private FeederType feederType;
    private Double incomerCtRatio;
    private String switchgearId;
    private Boolean isDedicatedBulkFeeder;
    @Column(name = "incomer_11kV_feeder_id")
    private String incomer11kVFeederId;
    @Column(name = "feeding_PTR_id")
    private String feedingPTRId;
    @Column(name = "feeding_33kV_feeder_id")
    private String feeding33kVFeederId;

}
