package com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity;

import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
import com.wbsedcl.trms.substation.log.domain.entity.FaultNature;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionType;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "interruption_master", schema = "substation_log_schema")
@Entity
@ToString
public class InterruptionEntity {
    @Id
    private String interruptionId;
    @OneToOne
    @JoinColumn(name="faulty_feeder_id")
    private FeederEntity faultyFeeder;
    @OneToOne
    @JoinColumn(name="substation_office_id", referencedColumnName = "officeId")
    private OfficeEntity substationOffice;
    @Enumerated(EnumType.STRING)
    private InterruptionType interruptionType;
    @Enumerated(EnumType.STRING)
    private FaultNature faultNature;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    @OneToOne
    @JoinColumn(name="created_by")
    private UserEntity createdByUser;
    @OneToOne
    @JoinColumn(name="restored_by")
    private UserEntity restoredByUser;
    @Enumerated(EnumType.STRING)
    private InterruptionStatus interruptionStatus;
    private LocalDateTime creationTimeStamp;
    private LocalDateTime restorationTimeStamp;
    private String cause;
}
