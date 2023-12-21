package com.wbsedcl.trms.feeder.management.messaging.mapper;

import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.feeder.management.domain.entity.FeederType;
import com.wbsedcl.trms.kafka.substation.log.avro.model.CreatedFeederAvroModel;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FeederManagementMessagingDataMapper {
    public CreatedFeederAvroModel feederDomainObjectToCreatedFeederAvroModel(Feeder feeder) {
        return CreatedFeederAvroModel.newBuilder()
                .setFeederId(feeder.getId().getValue())
                .setEnergyMeterNo(feeder.getEnergyMeterNoAtSource())
                .setSubstationOfficeId(feeder.getSourceSubstationOfficeId())
                .setFeederName(feeder.getFeederText())
                .setVoltageLevel(feeder.getVoltageLevel())
                .setFeederType(domainModelFeederTypeToAvroModelFeederType(feeder.getFeederType()))
                .setIncomer11kVFeederId(feeder.getIncomer11kVFeederId().getValue())
                .setFeedingPTRId(feeder.getFeedingPTRId().getValue())
                .setFeeding33kVFeederId(feeder.getFeeding33kVFeederId().getValue())
                .setSagaId(UUID.randomUUID())
                .build();
    }

    public com.wbsedcl.trms.kafka.substation.log.avro.model.FeederType domainModelFeederTypeToAvroModelFeederType(FeederType feederType) {
        switch (feederType) {
            case INCOMING_11KV ->
            {
                return com.wbsedcl.trms.kafka.substation.log.avro.model.FeederType.INCOMING_11kV;
            }
            case INCOMING_33KV -> {
                return com.wbsedcl.trms.kafka.substation.log.avro.model.FeederType.INCOMING_33kV;
            }
            case PTR_BAY -> {
                return com.wbsedcl.trms.kafka.substation.log.avro.model.FeederType.PTR_BAY;
            }
            case OUTGOING_33KV -> {
                return com.wbsedcl.trms.kafka.substation.log.avro.model.FeederType.OUTGOING_33kV;
            }
            case OUTGOING_11KV -> {
                return com.wbsedcl.trms.kafka.substation.log.avro.model.FeederType.OUTGOING_11kV;
            }
        }
        return null;
    }
}


