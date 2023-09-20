package com.wbsedcl.trms.feeder.management.messaging.publisher.kafka;

import com.wbsedcl.trms.feeder.management.messaging.mapper.FeederManagementMessagingDataMapper;
import com.wbsedcl.trms.feeder.management.domain.config.FeederManagementServiceConfigData;
import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.feeder.management.domain.event.FeederCreatedEvent;
import com.wbsedcl.trms.feeder.management.domain.ports.output.message.publish.FeederCreatedMessagePublisher;
import com.wbsedcl.trms.kafka.producer.service.KafkaProducer;
import com.wbsedcl.trms.kafka.substation.log.avro.model.CreatedFeederAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeederCreatedKafkaMessagePublisher implements FeederCreatedMessagePublisher {

    private final FeederManagementMessagingDataMapper feederManagementMessagingDataMapper;
    private final FeederManagementServiceConfigData feederManagementServiceConfigData;
    private final KafkaProducer<String, CreatedFeederAvroModel> kafkaProducer;

    public FeederCreatedKafkaMessagePublisher(FeederManagementMessagingDataMapper feederManagementMessagingDataMapper, FeederManagementServiceConfigData feederManagementServiceConfigData, KafkaProducer<String, CreatedFeederAvroModel> kafkaProducer) {
        this.feederManagementMessagingDataMapper = feederManagementMessagingDataMapper;
        this.feederManagementServiceConfigData = feederManagementServiceConfigData;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void publish(FeederCreatedEvent feederCreatedEvent) {
        //Get the feeder from the domain com.wbsedcl.hr.management.domain.event
        Feeder createdFeeder = feederCreatedEvent.getCreatedFeeder();
        //Log the com.wbsedcl.hr.management.domain.event
        log.info("Received feeder created com.wbsedcl.hr.management.domain.event with id {}", createdFeeder.getId().getValue());
        try {
            //Map the feeder to the avro model
            CreatedFeederAvroModel createdFeederAvroModel = feederManagementMessagingDataMapper
                    .feederDomainObjectToCreatedFeederAvroModel(createdFeeder);
            kafkaProducer.send(feederManagementServiceConfigData.getFeederCreatedTopicName(), createdFeeder.getId().getValue(), createdFeederAvroModel);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error sending FeederCreatedAvroModel message to Kafka with id {}: {}", createdFeeder.getId().getValue(), e.getMessage());
        }


    }
}
