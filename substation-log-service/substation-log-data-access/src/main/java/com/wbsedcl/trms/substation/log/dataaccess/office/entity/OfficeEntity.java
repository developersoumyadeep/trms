package com.wbsedcl.trms.substation.log.dataaccess.office.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ss_log_office_view")
@Entity
public class OfficeEntity {
    @Id
    private String officeId;
}
