//package com.wbsedcl.trms.substation.log.messaging.publisher.kafka;
//
//import com.wbsedcl.trms.kafka.producer.service.KafkaProducer;
//import com.wbsedcl.trms.kafka.substation.log.avro.model.LoadRecordAvroModel;
//import com.wbsedcl.trms.substation.log.domain.config.SubstationLogServiceConfigData;
//import com.wbsedcl.trms.substation.log.domain.event.LoadRecordLoggedEvent;
//import com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher.LoadRecordLoggedMessagePublisher;
//import com.wbsedcl.trms.substation.log.messaging.mapper.SubstationLogMessagingDataMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class LoadRecordLoggedKafkaMessagePublisher implements LoadRecordLoggedMessagePublisher {
//    private final SubstationLogMessagingDataMapper substationLogMessagingDataMapper;
//    private final SubstationLogServiceConfigData substationLogServiceConfigData;
//    private final KafkaProducer<String, LoadRecordAvroModel> kafkaProducer;
//
//    public LoadRecordLoggedKafkaMessagePublisher(SubstationLogMessagingDataMapper substationLogMessagingDataMapper,
//                                                        SubstationLogServiceConfigData substationLogServiceConfigData,
//                                                        KafkaProducer<String, LoadRecordAvroModel> kafkaProducer) {
//        this.substationLogMessagingDataMapper = substationLogMessagingDataMapper;
//        this.substationLogServiceConfigData = substationLogServiceConfigData;
//        this.kafkaProducer = kafkaProducer;
//    }
//    @Override
//    public void publish(LoadRecordLoggedEvent domainEvent) {
//        String loadRecordId = domainEvent.getRecord().getId().toString();
//        log.info("Received LoadRecordLoggedEvent for loadRecordId: {} ", loadRecordId);
//
//        try {
//            LoadRecordAvroModel loadRecordAvroModel = substationLogMessagingDataMapper
//                    .loadRecordLoggedEventToLoadRecordAvroModel(domainEvent);
//
//            kafkaProducer.send(substationLogServiceConfigData.getLoggedLoadRecordTopicName(), loadRecordId, loadRecordAvroModel);
//            log.info("LoadRecordAvroModel sent to Kafka for loadRecordId: {}",loadRecordId);
//        } catch (Exception e) {
//            log.error("Error while sending LoadRecordAvroModel message to Kafka with loadRecordId {}, error: {}", loadRecordId, e.getMessage());
//        }
//    }
//}
