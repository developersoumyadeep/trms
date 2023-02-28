package com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity;

import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "load_record_master")
@Entity
public class LoadRecordEntity {
    @Id
    private UUID loadRecordId;
    @OneToOne
    @JoinColumn(name="feeder_id")
    private FeederEntity feeder;
    @OneToOne
    @JoinColumn(name="substation_id")
    private OfficeEntity substationOffice;
    private LocalDate date;
    private LocalTime time;
    private  double load;
    @OneToOne
    @JoinColumn(name="recorded_by")
    private UserEntity recordedBy;

}
