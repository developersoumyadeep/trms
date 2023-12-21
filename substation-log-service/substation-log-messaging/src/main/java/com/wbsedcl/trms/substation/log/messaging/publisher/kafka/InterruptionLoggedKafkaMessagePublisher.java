//package com.wbsedcl.trms.substation.log.messaging.publisher.kafka;
//
//import com.wbsedcl.trms.kafka.producer.service.KafkaProducer;
//import com.wbsedcl.trms.kafka.substation.log.avro.model.LoggedInterruptionAvroModel;
//import com.wbsedcl.trms.substation.log.domain.config. SubstationLogServiceConfigData;
//import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;
//import com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher.InterruptionLoggedNotificationMessagePublisher;
//import com.wbsedcl.trms.substation.log.messaging.mapper.SubstationLogMessagingDataMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class InterruptionLoggedKafkaMessagePublisher implements InterruptionLoggedNotificationMessagePublisher {
//
//    private final SubstationLogMessagingDataMapper substationLogMessagingDataMapper;
//    private final SubstationLogServiceConfigData substationLogServiceConfigData;
//    private final KafkaProducer<String, LoggedInterruptionAvroModel> kafkaProducer;
//
//    public InterruptionLoggedKafkaMessagePublisher(SubstationLogMessagingDataMapper substationLogMessagingDataMapper,
//                                                   SubstationLogServiceConfigData substationLogServiceConfigData,
//                                                   KafkaProducer<String, LoggedInterruptionAvroModel> kafkaProducer) {
//        this.substationLogMessagingDataMapper = substationLogMessagingDataMapper;
//        this.substationLogServiceConfigData = substationLogServiceConfigData;
//        this.kafkaProducer = kafkaProducer;
//    }
//
//    @Override
//    public void publish(InterruptionLoggedEvent domainEvent) {
//        String interruptionId = domainEvent.getInterruption().getId().getValue().toString();
//        log.info("Received InterruptionLoggedEvent for interruption id: {}", interruptionId);
//
//        try {
//            LoggedInterruptionAvroModel loggedInterruptionAvroModel = substationLogMessagingDataMapper
//                    .interruptionLoggedEventToInterruptionLoggedAvroModel(domainEvent);
//
//            kafkaProducer.send(substationLogServiceConfigData.getLoggedInterruptionTopicName(), interruptionId, loggedInterruptionAvroModel);
//
//            log.info("InterruptionLoggedAvroModel sent to Kafka for interruptionId: {}", interruptionId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("Error while sending InterruptionLoggedAvroModel message to Kafka with interruptionId {}, error: {}", interruptionId, e.getMessage());
//        }
//    }
//}
