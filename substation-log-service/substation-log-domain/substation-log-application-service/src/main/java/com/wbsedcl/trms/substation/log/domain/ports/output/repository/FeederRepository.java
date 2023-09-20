package com.wbsedcl.trms.substation.log.domain.ports.output.repository;

import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.FeederLoadingHistory;

import java.util.List;
import java.util.Optional;

public interface FeederRepository {
    Optional<Feeder> findFeeder(String feederId);
    Optional<Feeder> saveFeeder(Feeder feeder);
    void updateFeeding33kVFeeder(String childFeederId, String parentFeederId);
    List<FeederLoadingHistory> findAllFeederLoadingHistoryByFeederId(String feederId);
    FeederLoadingHistory getOpenLoadingHistoryByFeederId(String feederId);
    void saveLoadingHistory(FeederLoadingHistory feederLoadingHistory);
    List<Feeder> findAllFeedersBySubstationOfficeId(String substationOfficeId);
}
