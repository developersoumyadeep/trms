package com.wbsedcl.trms.substation.log.security.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class User implements UserDetails{
    private String userId;
    private String firstName;
    private String lastName;
    private String officeId;
    private String officeName;
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
    private List<Role> roles;
    @Getter(AccessLevel.NONE)
    private boolean accountNotExpired;
    @Getter(AccessLevel.NONE)
    private boolean accountNotLocked;
    @Getter(AccessLevel.NONE)
    private boolean credentialsNotExpired;
    @Getter(AccessLevel.NONE)
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.authenticationString;
    }

    @Override
    public String getUsername() {
        return this.mobileNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
