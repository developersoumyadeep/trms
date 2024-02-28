package com.wbsedcl.trms.substation.log.dataaccess.feeder.repository;

import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeederJpaRepository extends JpaRepository<FeederEntity, String> {

    @Query(value = "select * from substation_log_schema.ss_log_feeder_m_view where feeding_33kV_feeder_id= :feederId",
    nativeQuery = true)
    List<FeederEntity> getChildFeedersOf33kVFeeder(@Param("feederId") String feederId);

    @Query(value ="select * from substation_log_schema.ss_log_feeder_m_view where substation_office_id = :o_Id", nativeQuery = true)
    List<FeederEntity> findAllBySubstationOfficeId(@Param("o_Id") String substationOfficeId);

    @Query(value = "select * from substation_log_schema.ss_log_feeder_m_view where feeding_ptr_id= :feederId",
            nativeQuery = true)
    List<FeederEntity> getChildFeedersOfPTR(@Param("feederId") String feederId);

}
