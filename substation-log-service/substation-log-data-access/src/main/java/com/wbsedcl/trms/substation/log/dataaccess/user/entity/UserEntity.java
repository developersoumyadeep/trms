package com.wbsedcl.trms.substation.log.dataaccess.user.entity;

import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ss_log_user_m_view", schema = "substation_log_schema")
@Entity
@ToString
public class UserEntity {
    @Id
    private String userId;
    @OneToOne
    @JoinColumn(name="office_id")
    private OfficeEntity office;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String authenticationString;
    private String sapId;
    private Boolean isDepartmental;
    private Boolean isContractual;
    private Boolean isReengaged;
    private Boolean isRetired;
    private Boolean reengagementContractExpired;
    private Boolean isTerminated;
    private Boolean isSuspended;
    private Boolean isRegular;
    private Boolean isVendor;
    private String companyName;
    private LocalDate joiningDateAtCurrentOffice;
    private LocalDate releaseDateFromPreviousOffice;
    private LocalDate dateOfRetirement;
    private LocalDate dateOfExpiryOfReengagementContract;
    private LocalDate dateOfBirth;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialsNotExpired;
    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER,targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="ss_log_user_role", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="role")
    private List<Role> roles;
}
