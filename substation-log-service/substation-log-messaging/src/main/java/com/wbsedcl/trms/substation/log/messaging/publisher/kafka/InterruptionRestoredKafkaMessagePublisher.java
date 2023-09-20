package com.wbsedcl.trms.substation.log.messaging.publisher.kafka;

import com.wbsedcl.trms.kafka.producer.service.KafkaProducer;
import com.wbsedcl.trms.kafka.substation.log.avro.model.RestoredInterruptionAvroModel;
import com.wbsedcl.trms.substation.log.domain.config.SubstationLogServiceConfigData;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionRestoredEvent;
import com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher.InterruptionRestoredNotificationMessagePublisher;
import com.wbsedcl.trms.substation.log.messaging.mapper.SubstationLogMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InterruptionRestoredKafkaMessagePublisher implements InterruptionRestoredNotificationMessagePublisher {
    private final SubstationLogMessagingDataMapper substationLogMessagingDataMapper;
    private final SubstationLogServiceConfigData substationLogServiceConfigData;
    private final KafkaProducer<String, RestoredInterruptionAvroModel> kafkaProducer;

    public InterruptionRestoredKafkaMessagePublisher(SubstationLogMessagingDataMapper substationLogMessagingDataMapper,
                                                   SubstationLogServiceConfigData substationLogServiceConfigData,
                                                   KafkaProducer<String, RestoredInterruptionAvroModel> kafkaProducer) {
        this.substationLogMessagingDataMapper = substationLogMessagingDataMapper;
        this.substationLogServiceConfigData = substationLogServiceConfigData;
        this.kafkaProducer = kafkaProducer;
    }
    @Override
    public void publish(InterruptionRestoredEvent domainEvent) {
        String interruptionId = domainEvent.getInterruption().getId().getValue().toString();
        log.info("Received InterruptionRestoredEvent for interruption id: {}", interruptionId);

        try {
            RestoredInterruptionAvroModel restoredInterruptionAvroModel = substationLogMessagingDataMapper
                    .interruptionRestoredEventToRestoredInterruptionAvroModel(domainEvent);

            kafkaProducer.send(substationLogServiceConfigData.getRestoredInterruptionTopicName(), interruptionId, restoredInterruptionAvroModel);

            log.info("InterruptionRestoredAvroModel sent to Kafka for interruptionId: {}", interruptionId);
        } catch (Exception e) {
            log.error("Error while sending InterruptionRestoredAvroModel message to Kafka with interruptionId {}, error: {}", interruptionId, e.getMessage());
        }
    }
}
