package com.wbsedcl.trms.substation.log.dataaccess.feeder.adapter;

import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederLoadingHistoryEntity;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.mapper.FeederDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.repository.FeederJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.repository.FeederLoadingHistoryJpaRepository;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.FeederLoadingHistory;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class FeederRepositoryImpl implements FeederRepository {
    private final FeederJpaRepository feederJpaRepository;

    private final FeederLoadingHistoryJpaRepository loadingHistoryJpaRepository;
    private final FeederDataAccessMapper mapper;

    public FeederRepositoryImpl(FeederJpaRepository feederJpaRepository, FeederLoadingHistoryJpaRepository loadingHistoryJpaRepository, FeederDataAccessMapper mapper) {
        this.feederJpaRepository = feederJpaRepository;
        this.loadingHistoryJpaRepository = loadingHistoryJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Feeder> findFeeder(String feederId) {
        Optional<FeederEntity> entity = feederJpaRepository.findById(feederId);
        if (entity.isPresent()){
            return Optional.of(mapper.feederEntityToFeeder(entity.get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Feeder> saveFeeder(Feeder feeder) {
        FeederEntity feederEntity = mapper.feederToFeederEntity(feeder);
        return Optional.of(mapper.feederEntityToFeeder(feederJpaRepository.save(feederEntity)));
    }

    @Override
    public void updateFeeding33kVFeeder(String childFeederId, String parentFeederId) {
        FeederEntity childFeederEntity = feederJpaRepository.findById(childFeederId).get();
        FeederEntity parentFeederEntity = feederJpaRepository.findById(parentFeederId).get();
        childFeederEntity.setFeeding33kVFeeder(parentFeederEntity);
        feederJpaRepository.save(childFeederEntity);
    }

    @Override
    public List<FeederLoadingHistory> findAllFeederLoadingHistoryByFeederId(String feederId) {
        List<FeederLoadingHistory> feederLoadingHistories = new ArrayList<>();
        List<FeederLoadingHistoryEntity> feederLoadingHistoryEntities =
                loadingHistoryJpaRepository.findAllByFeederId(feederId);
        for (FeederLoadingHistoryEntity entity : feederLoadingHistoryEntities) {
            feederLoadingHistories.add(mapper.feederLoadingHistoryEntityToLoadingHistoryDomainObject(entity));
        }
        return feederLoadingHistories;
    }

    @Override
    public FeederLoadingHistory getOpenLoadingHistoryByFeederId(String feederId) {
        FeederLoadingHistoryEntity openFeederLoadingHistoryEntity = loadingHistoryJpaRepository.getOpenLoadingHistoryByFeederId(feederId);
        log.info("openFeederLoadingHistoryEntity.getFeederEntity().getFeederId() : {}", openFeederLoadingHistoryEntity.getFeederEntity().getFeederId());
        FeederLoadingHistory openFeederLoadingHistory =  mapper.feederLoadingHistoryEntityToLoadingHistoryDomainObject(openFeederLoadingHistoryEntity);
        log.info("openFeederLoadingHistory.getFeederId(): {}",openFeederLoadingHistory.getFeederId());
        return openFeederLoadingHistory;
    }

    @Override
    public void saveLoadingHistory(FeederLoadingHistory feederLoadingHistory) {
        log.info("feederLoadingHistory.getFeederId() {}", feederLoadingHistory.getFeederId());
        log.info("feederLoadingHistory.getLoadedFromDate() : {}", feederLoadingHistory.getLoadedFromDate());
        log.info("feederLoadingHistory.getLoadedFromTime() : {}", feederLoadingHistory.getLoadedFromTime());
        log.info("feederLoadingHistory.getLoadedToDate() : {}", feederLoadingHistory.getLoadedToDate());
        log.info("feederLoadingHistory.getLoadedToTime() : {}", feederLoadingHistory.getLoadedToTime());
        loadingHistoryJpaRepository.save(mapper.feederLoadingHistoryDomainObjectTofeederLoadingHistoryEntity(feederLoadingHistory));
    }

    @Override
    public List<Feeder> findAllFeedersBySubstationOfficeId(String substationOfficeId) {
        List<FeederEntity> feederEntities = feederJpaRepository.findAllBySubstationOfficeId(substationOfficeId);
        List<Feeder> feeders = new ArrayList<>();
        for (FeederEntity entity : feederEntities) {
            feeders.add(mapper.feederEntityToFeeder(entity));
        }
        return feeders;
    }
}
