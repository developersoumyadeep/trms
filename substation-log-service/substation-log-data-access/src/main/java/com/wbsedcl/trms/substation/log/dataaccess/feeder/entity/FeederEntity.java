package com.wbsedcl.trms.substation.log.dataaccess.feeder.entity;

import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.domain.entity.FeederType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ss_log_feeder_view")
@Entity
public class FeederEntity {
    @Id
    private String feederId;
    private String feederName;
    private String energyMeterNo;

    @OneToOne
    @JoinColumn(name = "substation_id")
    private OfficeEntity substationOffice;
    private Integer voltageLevel;
    @Enumerated(EnumType.STRING)
    private FeederType feederType;
}
