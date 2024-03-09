package com.wbsedcl.trms.substation.log.dataaccess.feeder.mapper;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.FeederLoadingHistoryId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity.EnergyMeterEntity;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity.EnergyMeterFeederAssociationEntity;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederLoadingHistoryEntity;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.repository.EnergyMeterFeederAssociationJpaRepository;
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
    private final EnergyMeterFeederAssociationJpaRepository energyMeterFeederAssociationJpaRepository;

    public FeederDataAccessMapper(FeederJpaRepository feederJpaRepository, OfficeJpaRepository officeJpaRepository, EnergyMeterFeederAssociationJpaRepository energyMeterFeederAssociationJpaRepository) {
        this.feederJpaRepository = feederJpaRepository;
        this.officeJpaRepository = officeJpaRepository;
        this.energyMeterFeederAssociationJpaRepository = energyMeterFeederAssociationJpaRepository;
    }

    public FeederEntity feederToFeederEntity(Feeder feeder) {
        System.out.println("inside feederToFeederEntity(): "+ feeder);
        //Get the office entity from the database
        OfficeEntity substationOffice = officeJpaRepository.findByOfficeId(feeder.getSubstationOfficeId().getValue()).get();
        //Get the incoming feeder entity from the database
        FeederEntity incomer11kVFeeder = feeder.getFeederType() == FeederType.INCOMING_33kV
                || feeder.getFeederType() == FeederType.PTR_BAY
                || feeder.getFeederType() == FeederType.INCOMING_11kV
                || feeder.getFeederType() == FeederType.OUTGOING_33kV ? null : feederJpaRepository.findById(feeder.getIncomer11kVFeederId().getValue()).get();
        //Get the feeding PTR entity from the database
        FeederEntity feedingPTR = feeder.getFeederType() == FeederType.INCOMING_33kV
                || feeder.getFeederType() == FeederType.PTR_BAY
                || feeder.getFeederType() == FeederType.OUTGOING_33kV ? null : feederJpaRepository.findById(feeder.getFeedingPTRId().getValue()).get();
        //Get the feeding 33kV feeder entity from the database
        FeederEntity feeding33kVFeeder = feeder.getFeederType() == FeederType.INCOMING_33kV ? null : feederJpaRepository.findById(feeder.getFeeding33kVFeederId().getValue()).get();
        //Build the feeder entity
        return FeederEntity.builder()
                .feederId(feeder.getId().getValue())
                .feederName(feeder.getFeederName())
                .feederType(feeder.getFeederType())
                .substationOffice(substationOffice)
                .voltageLevel(feeder.getVoltageLevel())
                .incomer11kVFeeder(incomer11kVFeeder)
                .feedingPTR(feedingPTR)
                .feeding33kVFeeder(feeding33kVFeeder)
                .isCharged(feeder.getCharged())
                .isLoaded(feeder.isLoaded())
                .installedCTRatio(feeder.getInstalledCtRatio())
                .installedPTRatio(feeder.getInstalledPtRatio())
                .build();
    }
    
    public Feeder feederEntityToFeeder(FeederEntity feederEntity) {
        log.info("Getting energy meter entity for feeder "+feederEntity.getFeederId());
        EnergyMeterFeederAssociationEntity entity= energyMeterFeederAssociationJpaRepository.getCurrentlyInstalledEnergyMeterByFeederId(feederEntity.getFeederId());
        //Get energy meter no for the feeder id
        EnergyMeterEntity energyMeter = entity != null ? entity.getEnergyMeter() : null;
        //Build the domain model
        return Feeder.newBuilder()
                .feederId(new FeederId(feederEntity.getFeederId()))
                .feederName(feederEntity.getFeederName())
                .feederType(feederEntity.getFeederType())
                .energyMeterNo(energyMeter == null ? null : energyMeter.getEnergyMeterNo())
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
                .installedCtRatio(feederEntity.getInstalledCTRatio())
                .installedPtRatio(feederEntity.getInstalledPTRatio())
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
