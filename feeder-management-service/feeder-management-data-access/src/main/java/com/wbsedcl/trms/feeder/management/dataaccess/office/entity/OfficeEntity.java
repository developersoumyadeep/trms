package com.wbsedcl.trms.feeder.management.dataaccess.office.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feeder_management_office_m_view", schema = "feeder_management_schema")
@Entity
public class OfficeEntity {
    @Id
    private String officeId;
    private String officeText;
}
