package com.wbsedcl.trms.substation.log.dataaccess.user.repository;

import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    @Query(value ="select * from substation_log_schema.ss_log_user_m_view where mobile_number= :mobileNumber", nativeQuery = true)
    Optional<UserEntity> findByMobileNumber(@Param("mobileNumber") String mobileNumber);
}
