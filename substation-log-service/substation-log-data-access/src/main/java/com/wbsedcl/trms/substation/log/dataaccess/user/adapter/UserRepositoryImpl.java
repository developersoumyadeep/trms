package com.wbsedcl.trms.substation.log.dataaccess.user.adapter;

import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
import com.wbsedcl.trms.substation.log.dataaccess.user.mapper.UserDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.user.repository.UserJpaRepository;
import com.wbsedcl.trms.substation.log.domain.entity.User;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private UserJpaRepository userJpaRepository;
    private UserDataAccessMapper mapper;

    @Override
    public Optional<User> findUser(String userId) {
        Optional<UserEntity> userEntity =userJpaRepository.findById(userId);
        if(userEntity.isPresent()){
            return Optional.of(mapper.userEntityToUser(userEntity.get()));
        }
        return Optional.empty();
    }
}
