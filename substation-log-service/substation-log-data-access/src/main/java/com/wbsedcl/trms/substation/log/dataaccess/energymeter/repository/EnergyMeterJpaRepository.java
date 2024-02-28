package com.wbsedcl.trms.substation.log.dataaccess.energymeter.repository;

import com.wbsedcl.trms.domain.valueobject.EnergyMeterSerialNumber;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity.EnergyMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyMeterJpaRepository extends JpaRepository<EnergyMeterEntity, String> {
    @Query(value = "select * from substation_log_schema.ss_log_energy_meter_master where energy_meter_no= :energyMeterSerialNumber",
            nativeQuery = true)
    EnergyMeterEntity findEnergyMeterBySerialNo(@Param("energyMeterSerialNumber") String energyMeterSerialNumber);
}
