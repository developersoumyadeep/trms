package com.wbsedcl.trms.substation.log.dataaccess.substationlog.repository;

import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.InterruptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InterruptionLogJpaRepository extends JpaRepository<InterruptionEntity, String> {

    @Query(value ="select * from substation_log_schema.interruption_master where substation_office_id = :substationOfficeId", nativeQuery = true)
    List<InterruptionEntity> findAllBySubstationOfficeOfficeId(@Param("substationOfficeId") String substationOfficeId);
}
