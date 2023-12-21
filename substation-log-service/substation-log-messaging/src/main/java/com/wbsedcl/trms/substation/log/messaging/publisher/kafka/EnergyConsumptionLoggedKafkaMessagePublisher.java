//package com.wbsedcl.trms.substation.log.messaging.publisher.kafka;
//
//import com.wbsedcl.trms.kafka.producer.service.KafkaProducer;
//import com.wbsedcl.trms.kafka.substation.log.avro.model.EnergyConsumptionAvroModel;
//import com.wbsedcl.trms.substation.log.domain.config.SubstationLogServiceConfigData;
//import com.wbsedcl.trms.substation.log.domain.event.EnergyConsumptionLoggedEvent;
//import com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher.EnergyConsumptionLoggedMessagePublisher;
//import com.wbsedcl.trms.substation.log.messaging.mapper.SubstationLogMessagingDataMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class EnergyConsumptionLoggedKafkaMessagePublisher implements EnergyConsumptionLoggedMessagePublisher {
//    private final SubstationLogMessagingDataMapper substationLogMessagingDataMapper;
//    private final SubstationLogServiceConfigData substationLogServiceConfigData;
//    private final KafkaProducer<String, EnergyConsumptionAvroModel> kafkaProducer;
//
//    public EnergyConsumptionLoggedKafkaMessagePublisher(SubstationLogMessagingDataMapper substationLogMessagingDataMapper,
//                                                   SubstationLogServiceConfigData substationLogServiceConfigData,
//                                                   KafkaProducer<String, EnergyConsumptionAvroModel> kafkaProducer) {
//        this.substationLogMessagingDataMapper = substationLogMessagingDataMapper;
//        this.substationLogServiceConfigData = substationLogServiceConfigData;
//        this.kafkaProducer = kafkaProducer;
//    }
//    @Override
//    public void publish(EnergyConsumptionLoggedEvent domainEvent) {
//        String energyConsumptionId = domainEvent.getConsumption().getId().toString();
//        log.info("Received EnergyConsumptionLoggedEvent for energyConsumptionId: {} ", energyConsumptionId);
//
//        try {
//            EnergyConsumptionAvroModel energyConsumptionAvroModel = substationLogMessagingDataMapper
//                    .energyConsumptionLoggedEventToEnergyConsumptionAvroModel(domainEvent);
//
//            kafkaProducer.send(substationLogServiceConfigData.getLoggedEnergyConsumptionTopicName(), energyConsumptionId, energyConsumptionAvroModel);
//            log.info("EnergyConsumptionAvroModel sent to Kafka for energyConsumptionId: {}",energyConsumptionId);
//        } catch (Exception e) {
//            log.error("Error while sending EnergyConsumptionAvroModel message to Kafka with energyConsumptionId {}, error: {}", energyConsumptionId, e.getMessage());
//        }
//    }
//}
