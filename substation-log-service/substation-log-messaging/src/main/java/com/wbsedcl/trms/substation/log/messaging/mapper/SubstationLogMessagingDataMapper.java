package com.wbsedcl.trms.substation.log.messaging.mapper;


import com.wbsedcl.trms.kafka.substation.log.avro.model.*;
import com.wbsedcl.trms.substation.log.domain.dto.message.FeederCreatedResponse;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyConsumption;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyUnit;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.event.EnergyConsumptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionRestoredEvent;
import com.wbsedcl.trms.substation.log.domain.event.LoadRecordLoggedEvent;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoField;
import java.util.UUID;

import static com.wbsedcl.trms.kafka.substation.log.avro.model.FaultNature.*;
import static com.wbsedcl.trms.kafka.substation.log.avro.model.InterruptionStatus.NOT_RESTORED;
import static com.wbsedcl.trms.kafka.substation.log.avro.model.InterruptionStatus.RESTORED;
import static com.wbsedcl.trms.kafka.substation.log.avro.model.InterruptionType.*;

@Component
public class SubstationLogMessagingDataMapper {

   public  com.wbsedcl.trms.kafka.substation.log.avro.model.FaultNature domainFaultNatureToAvroModelFaultNature(com.wbsedcl.trms.substation.log.domain.entity.FaultNature faultNature){
       if (faultNature != null) {
           switch (faultNature) {
               case EF_OC -> {
                   return EF_OC;
               }
               case EF -> {
                   return EF;
               }
               case OC -> {
                   return OC;
               }
               case HIGH_SET_OC -> {
                   return HIGH_SET_OC;
               }
           }
       }
       return null;
    }

    public com.wbsedcl.trms.kafka.substation.log.avro.model.InterruptionType domainInterruptionTypeToAvroModelInterruptionType(com.wbsedcl.trms.substation.log.domain.entity.InterruptionType interruptionType) {
        switch (interruptionType) {
            case SOURCE_CHANGEOVER -> {
                return SOURCE_CHANGEOVER;
            }
            case TRANSIENT_TRIPPING -> {
                return TRANSIENT_TRIPPING;
            }
            case EMERGENCY_SHUTDOWN -> {
                return EMERGENCY_SHUTDOWN;
            }
            case SUSTAINED_FAULT -> {
                return SUSTAINED_FAULT;
            }
            case PLANNED_SHUTDOWN -> {
                return PLANNED_SHUTDOWN;
            }
            case LOAD_SHEDDING -> {
                return LOAD_SHEDDING;
            }
            case MAIN_POWER_FAIL -> {
                return MAIN_POWER_FAIL;
            }
        }
        return null;
    }

    public com.wbsedcl.trms.kafka.substation.log.avro.model.InterruptionStatus domainInterruptionStatusToAvroModelInterruptionStatus(com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus interruptionStatus) {
        switch (interruptionStatus) {
            case RESTORED -> {
                return RESTORED;
            }
            case NOT_RESTORED -> {
                return NOT_RESTORED;
            }
        }
        return null;
    }
    public LoggedInterruptionAvroModel interruptionLoggedEventToInterruptionLoggedAvroModel(InterruptionLoggedEvent event){
        Interruption interruption = event.getInterruption();
        return LoggedInterruptionAvroModel.newBuilder()
                .setInterruptionId(interruption.getId().getValue())
                .setSagaId(UUID.randomUUID())
                .setFaultyFeederId(interruption.getFaultyFeederId().getValue())
                .setSubstationOfficeId(interruption.getSubstationOfficeId().getValue())
                .setFaultNature(domainFaultNatureToAvroModelFaultNature(interruption.getFaultNature()))
                .setStartDate(interruption.getStartDate())
                .setStartTime(interruption.getStartTime().getLong(ChronoField.CLOCK_HOUR_OF_DAY))
                .setCreatedBy(interruption.getCreatedBy().getValue())
                .setInterruptionType(domainInterruptionTypeToAvroModelInterruptionType(interruption.getInterruptionType()))
                .setInterruptionStatus(domainInterruptionStatusToAvroModelInterruptionStatus(interruption.getInterruptionStatus()))
                .setEndDate(interruption.getEndDate())
                .setEndTime(interruption.getEndTime() == null ? null : interruption.getEndTime().getLong(ChronoField.CLOCK_HOUR_OF_DAY))
                .setRestoredBy(interruption.getRestoredBy().getValue())
                .setCause(interruption.getCause())
                .build();
    }

    public RestoredInterruptionAvroModel interruptionRestoredEventToRestoredInterruptionAvroModel(InterruptionRestoredEvent interruptionRestoredEvent) {
       Interruption interruption = interruptionRestoredEvent.getInterruption();
       return RestoredInterruptionAvroModel.newBuilder()
               .setInterruptionId(interruption.getId().getValue())
               .setFaultyFeederId(interruption.getFaultyFeederId().getValue())
               .setSubstationOfficeId(interruption.getSubstationOfficeId().getValue())
               .setSagaId(UUID.randomUUID())
               .setCause(interruption.getCause())
               .setRestoredBy(interruption.getRestoredBy().getValue())
               .setEndDate(interruption.getEndDate())
               .setEndTime(interruption.getEndTime().get(ChronoField.CLOCK_HOUR_OF_DAY))
               .build();
    }

    private com.wbsedcl.trms.kafka.substation.log.avro.model.EnergyUnit domainEnergyUnitToAvroModelEnergyUnit(EnergyUnit energyUnit) {
        switch (energyUnit) {
            case WH -> {
                return com.wbsedcl.trms.kafka.substation.log.avro.model.EnergyUnit.WH;
            }
            case KWH -> {
                return com.wbsedcl.trms.kafka.substation.log.avro.model.EnergyUnit.KWH;
            }
            case MWH -> {
                return com.wbsedcl.trms.kafka.substation.log.avro.model.EnergyUnit.MWH;
            }
        }
        return null;
    }

    public EnergyConsumptionAvroModel energyConsumptionLoggedEventToEnergyConsumptionAvroModel(EnergyConsumptionLoggedEvent energyConsumptionLoggedEvent) {
        EnergyConsumption energyConsumption = energyConsumptionLoggedEvent.getConsumption();
        return EnergyConsumptionAvroModel.newBuilder()
                .setEnergyConsumptionId(energyConsumption.getId().getValue())
                .setFeederId(energyConsumption.getFeederId().getValue())
                .setSubstationOfficeId(energyConsumption.getSubstationOfficeId().getValue())
                .setDate(energyConsumption.getDate())
                .setTime(energyConsumption.getTime().getLong(ChronoField.CLOCK_HOUR_OF_DAY))
                .setSagaId(UUID.randomUUID())
                .setConsumption(energyConsumption.getConsumption())
                .setEnergyMeterNo(energyConsumption.getEnergyMeterNo())
                .setEnergyUnit(domainEnergyUnitToAvroModelEnergyUnit(energyConsumption.getEnergyUnit()))
                .setMultiplyingFactor(energyConsumption.getMultiplyingFactor())
                .setRecordedBy(energyConsumption.getRecordedBy().getValue())
                .build();
    }

    public LoadRecordAvroModel loadRecordLoggedEventToLoadRecordAvroModel(LoadRecordLoggedEvent loadRecordLoggedEvent) {
        LoadRecord loadRecord = loadRecordLoggedEvent.getRecord();
        return LoadRecordAvroModel.newBuilder()
                .setLoadRecordId(loadRecord.getId().getValue())
                .setFeederId(loadRecord.getFeederId().getValue())
                .setSubstationOfficeId(loadRecord.getSubstationOfficeId().getValue())
                .setDate(loadRecord.getRecordDate())
                .setTime(loadRecord.getRecordTime().get(ChronoField.CLOCK_HOUR_OF_DAY))
                .setSagaId(UUID.randomUUID())
                .setLoad(loadRecord.getLoad())
                .setRecordedBy(loadRecord.getRecordedBy().getValue())
                .build();
    }

    public com.wbsedcl.trms.substation.log.domain.entity.FeederType avroModelFeederTypeToDomainModelFeederType(FeederType feederType) {
       switch (feederType) {
           case INCOMING_11kV ->
           {
               return com.wbsedcl.trms.substation.log.domain.entity.FeederType.INCOMING_11kV;
           }
           case INCOMING_33kV -> {
               return com.wbsedcl.trms.substation.log.domain.entity.FeederType.INCOMING_33kV;
           }
           case PTR_BAY -> {
               return com.wbsedcl.trms.substation.log.domain.entity.FeederType.PTR_BAY;
           }
           case OUTGOING_33kV -> {
               return com.wbsedcl.trms.substation.log.domain.entity.FeederType.OUTGOING_33kV;
           }
           case OUTGOING_11kV -> {
               return com.wbsedcl.trms.substation.log.domain.entity.FeederType.OUTGOING_11kV;
           }
       }
       return null;
    }

    public FeederCreatedResponse createdFeederAvroModelToFeederCreatedResponse(CreatedFeederAvroModel createdFeederAvroModel) {
        return FeederCreatedResponse.builder()
                .feederId(createdFeederAvroModel.getFeederId())
                .feederName(createdFeederAvroModel.getFeederName())
                .substationOfficeId(createdFeederAvroModel.getSubstationOfficeId())
                .energyMeterNo(createdFeederAvroModel.getEnergyMeterNo())
                .feederType(avroModelFeederTypeToDomainModelFeederType(createdFeederAvroModel.getFeederType()))
                .voltageLevel(createdFeederAvroModel.getVoltageLevel())
                .build();
    }
}
