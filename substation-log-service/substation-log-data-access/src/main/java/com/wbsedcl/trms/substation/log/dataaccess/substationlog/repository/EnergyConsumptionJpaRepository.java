package com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository;

import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.EnergyConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnergyConsumptionJpaRepository extends JpaRepository<EnergyConsumptionEntity, UUID> {
}
