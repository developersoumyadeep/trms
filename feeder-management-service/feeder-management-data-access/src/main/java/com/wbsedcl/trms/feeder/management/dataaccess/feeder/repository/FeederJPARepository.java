package com.wbsedcl.trms.feeder.management.dataaccess.feeder.repository;


import com.wbsedcl.trms.feeder.management.dataaccess.feeder.entity.FeederEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeederJPARepository extends JpaRepository<FeederEntity, String> {
}
