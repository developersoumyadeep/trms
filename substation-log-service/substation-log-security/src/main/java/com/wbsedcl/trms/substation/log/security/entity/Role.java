package com.wbsedcl.trms.substation.log.security.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    HR_USER, SITE_USER, SUPERUSER, SITE_SUPERVISOR, CONTROLLING_USER, DUMMY_USER;

    @Override
    public String getAuthority() {
        return "ROLE_"+this.name();
    }
}
