package com.wbsedcl.trms.feeder.management.domain;

import com.wbsedcl.trms.feeder.management.domain.dto.create.CreateFeederCommand;
import com.wbsedcl.trms.feeder.management.domain.dto.message.FeederCreatedResponse;
import com.wbsedcl.trms.feeder.management.domain.event.FeederCreatedEvent;
import com.wbsedcl.trms.feeder.management.domain.mapper.FeederDataMapper;
import com.wbsedcl.trms.feeder.management.domain.ports.output.message.publish.FeederCreatedMessagePublisher;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateFeederCommandHandler {
    private final FeederCreatedMessagePublisher publisher;
    private final FeederDataMapper feederDataMapper;
    private final CreateFeederHelper createFeederHelper;

    public CreateFeederCommandHandler(FeederCreatedMessagePublisher publisher, FeederDataMapper feederDataMapper, CreateFeederHelper createFeederHelper) {
        this.publisher = publisher;
        this.feederDataMapper = feederDataMapper;
        this.createFeederHelper = createFeederHelper;
    }

    public FeederCreatedResponse createFeeder(@Valid CreateFeederCommand command) {
        //persist the feeder
        FeederCreatedEvent feederCreatedEvent = createFeederHelper.persistFeeder(command);
        //log the info
        log.info("Feeder created with id {}", feederCreatedEvent.getCreatedFeeder().getId());
        //publish the com.wbsedcl.hr.management.domain.event
        publisher.publish(feederCreatedEvent);
        //return the response
        return feederDataMapper.feederToFeederCreatedResponse(feederCreatedEvent.getCreatedFeeder());
    }



}
