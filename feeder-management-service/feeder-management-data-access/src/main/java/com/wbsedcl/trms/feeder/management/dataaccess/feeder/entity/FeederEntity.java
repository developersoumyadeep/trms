package com.wbsedcl.trms.feeder.management.dataaccess.feeder.entity;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.feeder.management.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.feeder.management.domain.entity.FeederType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feeder_master", schema = "feeder_management_schema")
@Entity
public class FeederEntity {
    @Id
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
    private FeederType feederType;
    private String ptrId;
    private Double incomerCtRatio;
    private String switchgearId;
    private Boolean isDedicatedBulkFeeder;
    private FeederId incomer11kVFeederId;
    private FeederId feedingPTRId;
    private FeederId feeding33kVFeederId;

}
