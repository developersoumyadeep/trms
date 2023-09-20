package com.wbsedcl.trms.feeder.management.dataaccess.office.repository;


import com.wbsedcl.trms.feeder.management.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.feeder.management.dataaccess.office.entity.OfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeJPARepository extends JpaRepository<OfficeEntity, String> {
}
