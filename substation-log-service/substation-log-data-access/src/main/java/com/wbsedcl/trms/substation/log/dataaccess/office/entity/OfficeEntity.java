package com.wbsedcl.trms.substation.log.dataaccess.office.entity;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ss_log_office_m_view", schema = "substation_log_schema")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class OfficeEntity {
    @Id
    private String officeId;
    private String officeText;
    private String parentOfficeId;
}
