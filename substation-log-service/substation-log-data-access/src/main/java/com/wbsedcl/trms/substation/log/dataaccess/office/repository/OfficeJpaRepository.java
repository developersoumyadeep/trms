package com.wbsedcl.trms.substation.log.dataaccess.office.repository;

import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficeJpaRepository extends JpaRepository<OfficeEntity, String> {

    @Query(value = "select * from substation_log_schema.ss_log_office_m_view where office_id=:officeId", nativeQuery = true)
    Optional<OfficeEntity> findByOfficeId(@Param("officeId") String officeId);
}
