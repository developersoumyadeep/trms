package com.wbsedcl.trms.substation.log.dataaccess.energymeter.repository;

import com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity.EnergyMeterEntity;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity.EnergyMeterFeederAssociationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyMeterFeederAssociationJpaRepository extends JpaRepository<EnergyMeterFeederAssociationEntity, Integer> {
    @Query(value = "select * from substation_log_schema.ss_log_energy_meter_feeder_association where feeder_id= :feederId",
            nativeQuery = true)
    EnergyMeterFeederAssociationEntity getCurrentlyInstalledEnergyMeterByFeederId(@Param("feederId") String feederId);
}
