package com.wbsedcl.trms.substation.log.dataaccess.user.mapper;

import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
import com.wbsedcl.trms.substation.log.domain.entity.User;
import org.springframework.stereotype.Component;

/*
    This class maps from domain com.wbsedcl.hr.management.domain.entity to data access layer com.wbsedcl.hr.management.domain.entity and vice-versa
 */
@Component
public class UserDataAccessMapper {
    public UserEntity userToUserEntity(User user){
        OfficeEntity office = OfficeEntity.builder().officeId((user.getOfficeId()).getValue()).build();
        return UserEntity.builder()
                .userId(user.getId().getValue())
                .office(office)
                .build();
    }

    public User userEntityToUser(UserEntity userEntity) {
        return User.newBuilder()
                .officeId(new OfficeId(userEntity.getOffice().getOfficeId()))
                .userId(new UserId(userEntity.getUserId()))
                .build();
    }
}
