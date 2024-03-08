package com.wbsedcl.trms.substation.log.application.security.mapper;
import com.wbsedcl.trms.substation.log.application.security.entity.Role;
import com.wbsedcl.trms.substation.log.application.security.entity.User;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsMapper {

    public User userEntityToUserDetailsMapper(UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getUserId())
                .officeId(userEntity.getOffice().getOfficeId())
                .officeName(userEntity.getOffice().getOfficeText())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .mobileNumber(userEntity.getMobileNumber())
                .authenticationString(userEntity.getAuthenticationString())
                .sapId(userEntity.getSapId())
                .isDepartmental(userEntity.getIsDepartmental())
                .isContractual(userEntity.getIsContractual())
                .isReengaged(userEntity.getIsReengaged())
                .isRetired(userEntity.getIsRetired())
                .reengagementContractExpired(userEntity.getReengagementContractExpired())
                .isTerminated(userEntity.getIsTerminated())
                .isSuspended(userEntity.getIsSuspended())
                .isRegular(userEntity.getIsRegular())
                .isVendor(userEntity.getIsVendor())
                .companyName(userEntity.getCompanyName())
                .joiningDateAtCurrentOffice(userEntity.getJoiningDateAtCurrentOffice())
                .releaseDateFromPreviousOffice(userEntity.getReleaseDateFromPreviousOffice())
                .dateOfRetirement(userEntity.getDateOfRetirement())
                .dateOfExpiryOfReengagementContract(userEntity.getDateOfExpiryOfReengagementContract())
                .dateOfBirth(userEntity.getDateOfBirth())
                .accountNotExpired(userEntity.isAccountNotExpired())
                .accountNotLocked(userEntity.isAccountNotLocked())
                .credentialsNotExpired(userEntity.isCredentialsNotExpired())
                .enabled(userEntity.isEnabled())
                .roles(mapEntityRoleToSecurityRole(userEntity.getRoles())).build();
    }

    public UserEntity userDetailsToUserEntityMapper(User user) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .office(OfficeEntity.builder().officeId(user.getOfficeId()).officeText(user.getOfficeName()).build())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobileNumber(user.getMobileNumber())
                .authenticationString(user.getAuthenticationString())
                .sapId(user.getSapId())
                .isDepartmental(user.getIsDepartmental())
                .isContractual(user.getIsContractual())
                .isReengaged(user.getIsReengaged())
                .isRetired(user.getIsRetired())
                .reengagementContractExpired(user.getReengagementContractExpired())
                .isTerminated(user.getIsTerminated())
                .isSuspended(user.getIsSuspended())
                .isRegular(user.getIsRegular())
                .isVendor(user.getIsVendor())
                .companyName(user.getCompanyName())
                .joiningDateAtCurrentOffice(user.getJoiningDateAtCurrentOffice())
                .releaseDateFromPreviousOffice(user.getReleaseDateFromPreviousOffice())
                .dateOfRetirement(user.getDateOfRetirement())
                .dateOfExpiryOfReengagementContract(user.getDateOfExpiryOfReengagementContract())
                .dateOfBirth(user.getDateOfBirth())
                .accountNotExpired(user.isAccountNonExpired())
                .accountNotLocked(user.isAccountNonLocked())
                .credentialsNotExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .roles(mapSecurityRoleToEntityRole(user.getRoles())).build();
    }

    private List<Role> mapEntityRoleToSecurityRole(List<com.wbsedcl.trms.substation.log.dataaccess.user.entity.Role> roles) {
        return roles.stream()
                .map(role -> Role.valueOf(role.name()))
                .collect(Collectors.toList());
    }

    private List<com.wbsedcl.trms.substation.log.dataaccess.user.entity.Role> mapSecurityRoleToEntityRole(List<Role> roles) {
        return roles.stream()
                .map(role -> com.wbsedcl.trms.substation.log.dataaccess.user.entity.Role.valueOf(role.name()))
                .collect(Collectors.toList());
    }
}
