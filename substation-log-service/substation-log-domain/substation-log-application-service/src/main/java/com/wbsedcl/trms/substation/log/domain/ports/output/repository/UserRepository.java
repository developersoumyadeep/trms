package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

import com.wbsedcl.trms.substation.log.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUser(String userId);
}
