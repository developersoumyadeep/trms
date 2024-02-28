package com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository;

import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.EnergyMeterReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyMeterReadingJpaRepository extends JpaRepository<EnergyMeterReadingEntity, String> {
    @Query(value = "SELECT any_value(meter_reading_id) as meter_reading_id, any_value(feeder_id) as feeder_id, substation_office_id, max(record_date) as record_date, max(record_time) as record_time, any_value(energy_meter_no) as energy_meter_no, any_value(energy_unit) as energy_unit, max(meter_reading) as meter_reading, any_value(recorded_by) as recorded_by, any_value(multiplying_factor) as multiplying_factor from substation_log_schema.ss_log_energy_meter_reading_master where substation_office_id = :substationOfficeId group by any_value(energy_meter_no) order by max(record_date) desc, max(record_time) desc;", nativeQuery = true)
    public List<EnergyMeterReadingEntity> getLatestEnergyMeterReadingBySubstationOfficeId(@Param("substationOfficeId") String substationOfficeId);
}
