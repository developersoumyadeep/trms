package com.wbsedcl.trms.substation.log.dataaccess.substationlog.mapper;

import com.wbsedcl.trms.domain.valueobject.EnergyMeterSerialNumber;
import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.entity.EnergyMeterEntity;
import com.wbsedcl.trms.substation.log.dataaccess.energymeter.mapper.EnergyMeterDataAccessMapper;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.EnergyMeterReadingEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.InterruptionEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.LoadRecordEntity;
import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
//import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeterReading;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.EnergyMeterRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import com.wbsedcl.trms.substation.log.domain.valueobject.EnergyMeterReadingId;
import com.wbsedcl.trms.substation.log.domain.valueobject.InterruptionId;
import com.wbsedcl.trms.substation.log.domain.valueobject.LoadRecordId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/*
    This class maps from domain com.wbsedcl.hr.management.domain.entity to data access layer com.wbsedcl.hr.management.domain.entity and vice-versa
 */
@Component
@Slf4j
public class SubstationLogDataAccessMapper {

    private final FeederRepository feederRepository;
    private final EnergyMeterRepository energyMeterRepository;
    private final EnergyMeterDataAccessMapper energyMeterDataAccessMapper;

    public SubstationLogDataAccessMapper(FeederRepository feederRepository, EnergyMeterRepository energyMeterRepository, EnergyMeterDataAccessMapper energyMeterDataAccessMapper) {
        this.feederRepository = feederRepository;
        this.energyMeterRepository = energyMeterRepository;
        this.energyMeterDataAccessMapper = energyMeterDataAccessMapper;
    }

    public InterruptionEntity interruptionToInterruptionEntity(Interruption interruption) {
        FeederEntity faultyFeeder = FeederEntity.builder().feederId((interruption.getFaultyFeeder()).getId().getValue()).build();
        OfficeEntity substationOffice = OfficeEntity.builder().officeId((interruption.getSubstationOfficeId()).getValue()).build();
        UserEntity createdByUser = UserEntity.builder().userId((interruption.getCreatedBy()).getValue()).build();
        UserEntity restoredByUser = interruption.getRestoredBy() == null ? UserEntity.builder().userId("XXXXXXXX").build() : UserEntity.builder().userId((interruption.getRestoredBy()).getValue()).build();
        log.info("inside mapper function: interruption.getFaultyFeeder() : {}", interruption.getFaultyFeeder());
        log.info("inside mapper function: faultyFeeder : {}", faultyFeeder);
        return InterruptionEntity.builder()
                .interruptionId(interruption.getId().getValue().toString())
                .faultyFeeder(faultyFeeder)
                .substationOffice(substationOffice)
                .interruptionType(interruption.getInterruptionType())
                .faultNature(interruption.getFaultNature())
                .startDate(interruption.getStartDate())
                .startTime(interruption.getStartTime())
                .createdByUser(createdByUser)
                .endDate(interruption.getEndDate())
                .endTime(interruption.getEndTime())
                .restoredByUser(restoredByUser)
                .interruptionStatus(interruption.getInterruptionStatus())
                .cause(interruption.getCause())
                .creationTimeStamp(interruption.getCreationTimeStamp())
                .restorationTimeStamp(interruption.getRestorationTimeStamp())
                .build();
    }

    public Interruption interruptionEntityToInterruption(InterruptionEntity interruptionEntity) {
        Feeder faultyFeeder = feederRepository.findFeeder(interruptionEntity.getFaultyFeeder().getFeederId()).get();
        return Interruption.newBuilder()
                .interruptionId(new InterruptionId(UUID.fromString(interruptionEntity.getInterruptionId())))
                .faultyFeeder(faultyFeeder)
                .substationOfficeId(new OfficeId(interruptionEntity.getSubstationOffice().getOfficeId()))
                .interruptionType(interruptionEntity.getInterruptionType())
                .faultNature(interruptionEntity.getFaultNature())
                .startDate(interruptionEntity.getStartDate())
                .startTime(interruptionEntity.getStartTime())
                .createdBy(new UserId(interruptionEntity.getCreatedByUser().getUserId()))
                .endDate(interruptionEntity.getEndDate())
                .endTime(interruptionEntity.getEndTime())
                .restoredBy(new UserId(interruptionEntity.getRestoredByUser().getUserId()))
                .interruptionStatus(interruptionEntity.getInterruptionStatus())
                .cause(interruptionEntity.getCause())
                .creationTimeStamp(interruptionEntity.getCreationTimeStamp())
                .restorationTimeStamp(interruptionEntity.getRestorationTimeStamp())
                .build();
    }

    public EnergyMeterReadingEntity energyMeterReadingToEnergyMeterReadingEntity(EnergyMeterReading energyMeterReading) {
        FeederEntity feederEntity = FeederEntity.builder().feederId(energyMeterReading.getFeederId().getValue()).build();
        OfficeEntity officeEntity = OfficeEntity.builder().officeId(energyMeterReading.getSubstationOfficeId().getValue()).build();
        UserEntity userEntity = UserEntity.builder().userId(energyMeterReading.getRecordedBy().getValue()).build();
        EnergyMeterEntity energyMeterEntity = energyMeterDataAccessMapper
                .energyMeterToEnergyMeterEntity(energyMeterRepository
                        .findEnergyMeterBySerialNumber(energyMeterReading
                                .getEnergyMeterNo().getValue()).get());

        //We are not adding mf here as it will be calculated automatically before persisting
        return EnergyMeterReadingEntity.builder()
                .meterReadingId(energyMeterReading.getId().getValue().toString())
                .feeder(feederEntity)
                .substationOffice(officeEntity)
                .recordDate(energyMeterReading.getRecordDate())
                .recordTime(energyMeterReading.getRecordTime())
                .energyMeter(energyMeterEntity)
                .energyUnit(energyMeterReading.getEnergyUnit())
                .meterReading(energyMeterReading.getMeterReading())
                .recordedBy(userEntity)
                .build();
    }

    public EnergyMeterReading energyMeterReadingEntityToEnergyMeterReading(EnergyMeterReadingEntity energyMeterReadingEntity) {
        return EnergyMeterReading.builder()
                .id(new EnergyMeterReadingId(UUID.fromString(energyMeterReadingEntity.getMeterReadingId())))
                .feederId(new FeederId(energyMeterReadingEntity.getFeeder().getFeederId()))
                .energyMeterNo(new EnergyMeterSerialNumber(energyMeterReadingEntity.getEnergyMeter().getEnergyMeterNo()))
                .substationOfficeId(new OfficeId(energyMeterReadingEntity.getSubstationOffice().getOfficeId()))
                .meterReading(energyMeterReadingEntity.getMeterReading())
                .recordDate(energyMeterReadingEntity.getRecordDate())
                .recordTime(energyMeterReadingEntity.getRecordTime())
                .energyUnit(energyMeterReadingEntity.getEnergyUnit())
                .recordedBy(new UserId(energyMeterReadingEntity.getRecordedBy().getUserId()))
                .build();
    }

    public LoadRecordEntity loadRecordToLoadRecordEntity(LoadRecord loadRecord) {
        FeederEntity feeder = FeederEntity.builder().feederId((loadRecord.getFeederId()).getValue()).build();
        UserEntity recordedByUser = UserEntity.builder().userId((loadRecord.getRecordedBy()).getValue()).build();
        OfficeEntity substationOffice = OfficeEntity.builder().officeId((loadRecord.getSubstationOfficeId()).getValue()).build();

        return LoadRecordEntity.builder()
                .loadRecordId(loadRecord.getId().getValue().toString())
                .feeder(feeder)
                .substationOffice(substationOffice)
                .recordDate(loadRecord.getRecordDate())
                .recordTime(loadRecord.getRecordTime())
                .recordedLoad(loadRecord.getLoad())
                .recordedBy(recordedByUser)
                .remarks(loadRecord.getRemarks())
                .feederLoadingType(loadRecord.getLoadRecordType())
                .build();
    }

    public LoadRecord loadRecordEntityToLoadRecord(LoadRecordEntity loadRecordEntity) {
        return LoadRecord.newBuilder()
                .loadRecordId(new LoadRecordId(UUID.fromString(loadRecordEntity.getLoadRecordId())))
                .feederId(new FeederId(loadRecordEntity.getFeeder().getFeederId()))
                .substationOfficeId(new OfficeId(loadRecordEntity.getSubstationOffice().getOfficeId()))
                .recordedBy(new UserId(loadRecordEntity.getRecordedBy().getUserId()))
                .load(loadRecordEntity.getRecordedLoad())
                .recordDate(loadRecordEntity.getRecordDate())
                .recordTime(loadRecordEntity.getRecordTime())
                .remarks(loadRecordEntity.getRemarks())
                .loadRecordType(loadRecordEntity.getFeederLoadingType())
                .build();
    }
}
