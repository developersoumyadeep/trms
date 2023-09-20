package com.wbsedcl.trms.substation.log.messaging.listener.kafka;

import com.wbsedcl.trms.kafka.consumer.service.KafkaConsumer;
import com.wbsedcl.trms.kafka.substation.log.avro.model.CreatedFeederAvroModel;
import com.wbsedcl.trms.substation.log.domain.dto.message.FeederCreatedResponse;
import com.wbsedcl.trms.substation.log.domain.ports.input.message.listener.feeder.FeederCreatedMessageListener;
import com.wbsedcl.trms.substation.log.messaging.mapper.SubstationLogMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class FeederCreatedKafkaMessageListener implements KafkaConsumer<CreatedFeederAvroModel> {

    private final FeederCreatedMessageListener feederCreatedMessageListener;
    private final SubstationLogMessagingDataMapper substationLogMessagingDataMapper;

    public FeederCreatedKafkaMessageListener(FeederCreatedMessageListener feederCreatedMessageListener,
                                             SubstationLogMessagingDataMapper substationLogMessagingDataMapper) {
        this.feederCreatedMessageListener = feederCreatedMessageListener;
        this.substationLogMessagingDataMapper = substationLogMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.feeder-service-consumer-group-id}", topics = "${substation-log-service.created-feeder-topic-name}")
    public void receive(@Payload List<CreatedFeederAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<Long> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partition,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        messages.forEach(createdFeederAvroModel -> {
            log.info("Received kafka message for feeder id {}", createdFeederAvroModel.getFeederId());
            log.info("Received feeder name {}", createdFeederAvroModel.getFeederName());
            log.info("Received feeder officeId {}", createdFeederAvroModel.getSubstationOfficeId());
            feederCreatedMessageListener.saveNewlyCreatedFeeder(substationLogMessagingDataMapper.createdFeederAvroModelToFeederCreatedResponse(createdFeederAvroModel));
        });
    }
}
