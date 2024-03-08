package com.wbsedcl.trms.substation.log.application.security.repository;

import com.wbsedcl.trms.substation.log.application.security.entity.User;
import com.wbsedcl.trms.substation.log.application.security.mapper.UserDetailsMapper;
import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
import com.wbsedcl.trms.substation.log.dataaccess.user.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsRepositoryImpl implements UserDetailsRepository{

    private Logger logger = LoggerFactory.getLogger(UserDetailsRepositoryImpl.class);
    private final UserJpaRepository userJpaRepository;
    private final UserDetailsMapper userDetailsMapper;

    public UserDetailsRepositoryImpl(UserJpaRepository userJpaRepository, UserDetailsMapper userDetailsMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public Optional<User> findByMobileNumber(String mobileNumber) {
        logger.debug("Getting user from db with mobile number {}", mobileNumber);
        UserEntity userEntity = userJpaRepository.findByMobileNumber(mobileNumber).orElseThrow();
        return Optional.of(userDetailsMapper.userEntityToUserDetailsMapper(userEntity));
    }
}
