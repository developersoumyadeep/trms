package com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository;

import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.InterruptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InterruptionLogJpaRepository extends JpaRepository<InterruptionEntity, String> {

}
