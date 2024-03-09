package com.wbsedcl.trms.substation.log.security.repository;

import com.wbsedcl.trms.substation.log.security.entity.User;

import java.util.Optional;

public interface UserDetailsRepository {
    Optional<User> findByMobileNumber(String mobileNumber);
}
