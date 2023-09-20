package com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity;

import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecordType;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "load_record_master", schema = "substation_log_schema")
@Entity
public class LoadRecordEntity {
    @Id
    private String loadRecordId;
    @OneToOne
    @JoinColumn(name="feeder_id")
    private FeederEntity feeder;
    @OneToOne
    @JoinColumn(name="substation_office_id")
    private OfficeEntity substationOffice;
    private LocalDate recordDate;
    private LocalTime recordTime;
    private  double recordedLoad;
    @OneToOne
    @JoinColumn(name="recorded_by")
    private UserEntity recordedBy;
    private String remarks;
    private LoadRecordType loadRecordType;

}
