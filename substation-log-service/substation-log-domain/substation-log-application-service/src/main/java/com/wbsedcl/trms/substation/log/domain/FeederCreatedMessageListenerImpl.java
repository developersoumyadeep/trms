package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.message.FeederCreatedResponse;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.mapper.FeederServiceDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.input.message.listener.feeder.FeederCreatedMessageListener;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeederCreatedMessageListenerImpl implements FeederCreatedMessageListener {

    private final FeederServiceDataMapper feederServiceDataMapper;
    private final FeederRepository feederRepository;

    public FeederCreatedMessageListenerImpl(FeederServiceDataMapper feederServiceDataMapper,
                                            FeederRepository feederRepository) {
        this.feederServiceDataMapper = feederServiceDataMapper;
        this.feederRepository = feederRepository;
    }

    @Override
    public void saveNewlyCreatedFeeder(FeederCreatedResponse feederCreatedResponse) {
        log.info("feederCreatedResponse.getFeederName(): {}", feederCreatedResponse.getFeederName());
        log.info("feederCreatedResponse.getIncomer11kVFeederId(): {}", feederCreatedResponse.getIncomer11kVFeederId());
        log.info("feederCreatedResponse.getFeedingPTRId(): {}", feederCreatedResponse.getFeedingPTRId());
        log.info("feederCreatedResponse.getFeeding33kVFeederId(): {}", feederCreatedResponse.getFeeding33kVFeederId());
        //1. Map the response object to the domain object
        Feeder feeder = feederServiceDataMapper.feederCreatedResponseToFeederDomainObject(feederCreatedResponse);
        log.info("feeder.getFeederName() : {}", feeder.getFeederName());
        //2. Save the domain object using FeederRepository output port
        log.info("Saving the newly created feeder {} with id {}", feeder.getFeederName(), feeder.getId().getValue());
        feederRepository.saveFeeder(feeder);
    }
}
