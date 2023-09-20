package com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository;

import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.LoadRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoadRecordJpaRepository extends JpaRepository<LoadRecordEntity, UUID> {
}
