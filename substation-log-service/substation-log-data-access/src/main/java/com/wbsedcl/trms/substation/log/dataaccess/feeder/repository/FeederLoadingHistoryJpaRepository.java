package com.wbsedcl.trms.substation.log.dataaccess.feeder.repository;

import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederLoadingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeederLoadingHistoryJpaRepository extends JpaRepository<FeederLoadingHistoryEntity, Integer> {
    @Query( value = "select * from substation_log_schema.feeder_loading_history where feeder_id = :feederId", nativeQuery = true)
    List<FeederLoadingHistoryEntity> findAllByFeederId(String feederId);

    @Query( value = "select * from substation_log_schema.feeder_loading_history where loaded_to_date is null and feeder_id = :feederId", nativeQuery = true)
    FeederLoadingHistoryEntity getOpenLoadingHistoryByFeederId(String feederId);
}