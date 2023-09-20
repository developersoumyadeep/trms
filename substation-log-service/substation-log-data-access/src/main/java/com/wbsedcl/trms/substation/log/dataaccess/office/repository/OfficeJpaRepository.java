package com.wbsedcl.trms.substation.log.dataaccess.office.repository;

import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeJpaRepository extends JpaRepository<OfficeEntity, String> {
}
