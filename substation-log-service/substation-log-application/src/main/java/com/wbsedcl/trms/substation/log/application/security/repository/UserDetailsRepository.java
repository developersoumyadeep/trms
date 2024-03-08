package com.wbsedcl.trms.substation.log.application.security.repository;

import com.wbsedcl.trms.substation.log.application.security.entity.User;

import java.util.Optional;

public interface UserDetailsRepository {
    public Optional<User> findByMobileNumber(String mobileNumber);
}
