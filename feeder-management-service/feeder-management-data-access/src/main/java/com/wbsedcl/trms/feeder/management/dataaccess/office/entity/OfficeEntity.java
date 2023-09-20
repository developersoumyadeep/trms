package com.wbsedcl.trms.feeder.management.dataaccess.office.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
