package com.wbsedcl.trms.substation.log.dataaccess.office.repository;

import com.wbsedcl.trms.substation.log.dataaccess.office.entity.SubstationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubstationJpaRepository extends JpaRepository<SubstationEntity, String> {
    Optional<SubstationEntity>  findByOfficeId(String officeId);
}
