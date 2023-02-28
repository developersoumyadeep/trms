package com.wbsedcl.trms.substation.log.dataaccess.substationlog.mapper;

import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.substation.log.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.EnergyConsumptionEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.InterruptionEntity;
import com.wbsedcl.trms.substation.log.dataaccess.substationlog.entity.LoadRecordEntity;
import com.wbsedcl.trms.substation.log.dataaccess.user.entity.UserEntity;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.valueobject.EnergyConsumptionId;
import com.wbsedcl.trms.substation.log.domain.valueobject.FeederId;
import com.wbsedcl.trms.substation.log.domain.valueobject.InterruptionId;
import com.wbsedcl.trms.substation.log.domain.valueobject.LoadRecordId;
import org.springframework.stereotype.Component;

@Component
public class SubstationLogDataAccessMapper {

    public InterruptionEntity interruptionToInterruptionEntity(Interruption interruption) {
      FeederEntity faultyFeeder = FeederEntity.builder().feederId((interruption.getFaultyFeederId()).getValue()).build();
      OfficeEntity substationOffice = OfficeEntity.builder().officeId((interruption.getSubstationOfficeId()).getValue()).build();
      UserEntity createdByUser = UserEntity.builder().userId((interruption.getCreatedBy()).getValue()).build();
      UserEntity restoredByUser = UserEntity.builder().userId((interruption.getRestoredBy()).getValue()).build();
      return InterruptionEntity.builder()
              .interruptionId(interruption.getId().getValue())
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
              .cause(interruption.getCause())
              .creationTimeStamp(interruption.getCreationTimeStamp())
              .restorationTimeStamp(interruption.getRestorationTimeStamp())
              .build();
    }
    
    public Interruption interruptionEntityToInterruption(InterruptionEntity interruptionEntity) {
        return Interruption.newBuilder()
                .interruptionId(new InterruptionId(interruptionEntity.getInterruptionId()))
                .faultyFeederId(new FeederId(interruptionEntity.getFaultyFeeder().getFeederId()))
                .substationOfficeId(new OfficeId(interruptionEntity.getSubstationOffice().getOfficeId()))
                .interruptionType(interruptionEntity.getInterruptionType())
                .faultNature(interruptionEntity.getFaultNature())
                .startDate(interruptionEntity.getStartDate())
                .startTime(interruptionEntity.getStartTime())
                .createdBy(new UserId(interruptionEntity.getCreatedByUser().getUserId()))
                .endDate(interruptionEntity.getEndDate())
                .endTime(interruptionEntity.getEndTime())
                .restoredBy(new UserId(interruptionEntity.getRestoredByUser().getUserId()))
                .cause(interruptionEntity.getCause())
                .creationTimeStamp(interruptionEntity.getCreationTimeStamp())
                .restorationTimeStamp(interruptionEntity.getRestorationTimeStamp())
                .build();
    }

    public EnergyConsumptionEntity energyConsumptionToEnergyConsumptionEntity(EnergyConsumption energyConsumption) {
        FeederEntity feeder = FeederEntity.builder().feederId((energyConsumption.getFeederId()).getValue()).build();
        UserEntity recordedByUser = UserEntity.builder().userId((energyConsumption.getRecordedBy()).getValue()).build();
        OfficeEntity substationOffice = OfficeEntity.builder().officeId((energyConsumption.getSubstationOfficeId()).getValue()).build();
        return EnergyConsumptionEntity.builder()
                .consumptionId(energyConsumption.getId().getValue())
                .energyMeterNo(energyConsumption.getEnergyMeterNo())
                .feeder(feeder)
                .date(energyConsumption.getDate())
                .time(energyConsumption.getTime())
                .consumption(energyConsumption.getConsumption())
                .energyUnit(energyConsumption.getEnergyUnit())
                .multiplyingFactor(energyConsumption.getMultiplyingFactor())
                .recordedBy(recordedByUser)
                .substationOffice(substationOffice)
                .build();
    }

    public EnergyConsumption energyConsumptionEntityToEnergyConsumption(EnergyConsumptionEntity energyConsumptionEntity){
        return EnergyConsumption.newBuilder()
                .id(new EnergyConsumptionId(energyConsumptionEntity.getConsumptionId()))
                .energyMeterNo(energyConsumptionEntity.getEnergyMeterNo())
                .feederId(new FeederId(energyConsumptionEntity.getFeeder().getFeederId()))
                .date(energyConsumptionEntity.getDate())
                .time(energyConsumptionEntity.getTime())
                .consumption(energyConsumptionEntity.getConsumption())
                .energyUnit(energyConsumptionEntity.getEnergyUnit())
                .multiplyingFactor(energyConsumptionEntity.getMultiplyingFactor())
                .recordedBy(new UserId(energyConsumptionEntity.getRecordedBy().getUserId()))
                .substationOfficeId(new OfficeId(energyConsumptionEntity.getSubstationOffice().getOfficeId()))
                .build();
    }
    
    public LoadRecordEntity loadRecordToLoadRecordEntity(LoadRecord loadRecord) {
        FeederEntity feeder = FeederEntity.builder().feederId((loadRecord.getFeederId()).getValue()).build();
        UserEntity recordedByUser = UserEntity.builder().userId((loadRecord.getRecordedBy()).getValue()).build();
        OfficeEntity substationOffice = OfficeEntity.builder().officeId((loadRecord.getSubstationOfficeId()).getValue()).build();

        return LoadRecordEntity.builder()
                .loadRecordId(loadRecord.getId().getValue())
                .feeder(feeder)
                .substationOffice(substationOffice)
                .date(loadRecord.getDate())
                .time(loadRecord.getTime())
                .load(loadRecord.getLoad())
                .recordedBy(recordedByUser)
                .build();
    }

    public LoadRecord loadRecordEntityToLoadRecord(LoadRecordEntity loadRecordEntity) {
        return LoadRecord.newBuilder()
                .loadRecordId(new LoadRecordId(loadRecordEntity.getLoadRecordId()))
                .feederId(new FeederId(loadRecordEntity.getFeeder().getFeederId()))
                .substationOfficeId(new OfficeId(loadRecordEntity.getSubstationOffice().getOfficeId()))
                .recordedBy(new UserId(loadRecordEntity.getRecordedBy().getUserId()))
                .load(loadRecordEntity.getLoad())
                .date(loadRecordEntity.getDate())
                .time(loadRecordEntity.getTime())
                .build();
    }
}
