package com.wbsedcl.trms.substation.log.dataaccess.feeder.repository;

import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeederJpaRepository extends JpaRepository<FeederEntity, String> {
}
