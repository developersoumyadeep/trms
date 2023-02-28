package com.wbsedcl.trms.substation.log.dataaccess.user.entity;

import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ss_log_user_view")
@Entity
public class UserEntity {
    @Id
    private String userId;
    @OneToOne
    @JoinColumn(name="office_id")
    private OfficeEntity office;
}
