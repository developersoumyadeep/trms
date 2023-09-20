package com.wbsedcl.trms.substation.log.dataaccess.feeder.mapper;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.FeederLoadingHistoryId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederLoadingHistoryEntity;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.repository.FeederJpaRepository;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.repository.OfficeJpaRepository;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.FeederLoadingHistory;
import com.wbsedcl.trms.substation.log.domain.entity.FeederType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/*
    This class maps from domain com.wbsedcl.hr.management.domain.entity to data access layer com.wbsedcl.hr.management.domain.entity and vice-versa
 */
@Slf4j
@Component
public class FeederDataAccessMapper {

    private final FeederJpaRepository feederJpaRepository;
    private final OfficeJpaRepository officeJpaRepository;

    public FeederDataAccessMapper(FeederJpaRepository feederJpaRepository, OfficeJpaRepository officeJpaRepository) {
        this.feederJpaRepository = feederJpaRepository;
        this.officeJpaRepository = officeJpaRepository;
    }

    public FeederEntity feederToFeederEntity(Feeder feeder) {
        OfficeEntity substationOffice = officeJpaRepository.findById(feeder.getSubstationOfficeId().getValue()).get();
        FeederEntity incomer11kVFeeder = feeder.getFeederType() == FeederType.INCOMING_33kV
                || feeder.getFeederType() == FeederType.PTR_BAY
                || feeder.getFeederType() == FeederType.INCOMING_11kV
                || feeder.getFeederType() == FeederType.OUTGOING_33kV ? null : feederJpaRepository.findById(feeder.getIncomer11kVFeederId().getValue()).get();
        FeederEntity feedingPTR = feeder.getFeederType() == FeederType.INCOMING_33kV
                || feeder.getFeederType() == FeederType.PTR_BAY
                || feeder.getFeederType() == FeederType.OUTGOING_33kV ? null : feederJpaRepository.findById(feeder.getFeedingPTRId().getValue()).get();
        FeederEntity feeding33kVFeeder = feeder.getFeederType() == FeederType.INCOMING_33kV ? null : feederJpaRepository.findById(feeder.getFeeding33kVFeederId().getValue()).get();
        return FeederEntity.builder()
                .feederId(feeder.getId().getValue())
                .feederName(feeder.getFeederName())
                .feederType(feeder.getFeederType())
                .energyMeterNo(feeder.getEnergyMeterNo())
                .substationOffice(substationOffice)
                .voltageLevel(feeder.getVoltageLevel())
                .incomer11kVFeeder(incomer11kVFeeder)
                .feedingPTR(feedingPTR)
                .feeding33kVFeeder(feeding33kVFeeder)
                .isCharged(feeder.getCharged())
                .isLoaded(feeder.isLoaded())
                .build();
    }
    
    public Feeder feederEntityToFeeder(FeederEntity feederEntity) {
        return Feeder.newBuilder()
                .feederId(new FeederId(feederEntity.getFeederId()))
                .feederName(feederEntity.getFeederName())
                .feederType(feederEntity.getFeederType())
                .energyMeterNo(feederEntity.getEnergyMeterNo())
                .substationOfficeId(new OfficeId(feederEntity.getSubstationOffice().getOfficeId()))
                .voltageLevel(feederEntity.getVoltageLevel())
                .incomer11kVFeederId(feederEntity.getFeederType() == FeederType.INCOMING_33kV
                        || feederEntity.getFeederType() == FeederType.PTR_BAY
                        || feederEntity.getFeederType() == FeederType.INCOMING_11kV
                        || feederEntity.getFeederType() == FeederType.OUTGOING_33kV ? null : new FeederId(feederEntity.getIncomer11kVFeeder().getFeederId()))
                .feedingPTRId(feederEntity.getFeederType() == FeederType.INCOMING_33kV
                        || feederEntity.getFeederType() == FeederType.PTR_BAY
                        || feederEntity.getFeederType() == FeederType.OUTGOING_33kV ? null : new FeederId(feederEntity.getFeedingPTR().getFeederId()))
                .feeding33kVFeederId(feederEntity.getFeederType() == FeederType.INCOMING_33kV ? null : new FeederId(feederEntity.getFeeding33kVFeeder().getFeederId()) )
                .isCharged(feederEntity.getIsCharged())
                .isLoaded(feederEntity.getIsLoaded())
                .build();
    }

    public FeederLoadingHistory feederLoadingHistoryEntityToLoadingHistoryDomainObject(FeederLoadingHistoryEntity entity) {

        return FeederLoadingHistory.builder()
                .feederLoadingHistoryId(new FeederLoadingHistoryId(entity.getHistoryId()))
                .feederId(entity.getFeederEntity().getFeederId())
                .loadedFromDate(entity.getLoadedFromDate())
                .loadedFromTime(entity.getLoadedFromTime())
                .loadedToDate(entity.getLoadedToDate())
                .loadedToTime(entity.getLoadedToTime())
                .build();
    }

    public FeederLoadingHistoryEntity feederLoadingHistoryDomainObjectTofeederLoadingHistoryEntity(FeederLoadingHistory feederLoadingHistory){
        FeederEntity feederEntity = feederJpaRepository.findById(feederLoadingHistory.getFeederId()).get();
        return FeederLoadingHistoryEntity.builder()
                .historyId(feederLoadingHistory.getId() == null ? null : feederLoadingHistory.getId().getValue())
                .feederEntity(feederEntity)
                .loadedFromDate(feederLoadingHistory.getLoadedFromDate())
                .loadedFromTime(feederLoadingHistory.getLoadedFromTime())
                .loadedToDate(feederLoadingHistory.getLoadedToDate())
                .loadedToTime(feederLoadingHistory.getLoadedToTime())
                .build();
    }
}
