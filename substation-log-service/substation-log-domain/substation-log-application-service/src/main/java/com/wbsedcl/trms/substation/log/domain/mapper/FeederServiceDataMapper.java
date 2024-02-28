package com.wbsedcl.trms.substation.log.domain.mapper;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.substation.log.domain.dto.message.FeederDTO;
import com.wbsedcl.trms.substation.log.domain.dto.message.FeederCreatedResponse;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FeederServiceDataMapper {

    private final FeederRepository feederRepository;

    public FeederServiceDataMapper(FeederRepository feederRepository) {
        this.feederRepository = feederRepository;
    }


    public Feeder feederCreatedResponseToFeederDomainObject(FeederCreatedResponse feederCreatedResponse) {
        return Feeder.newBuilder()
                .feederId(new FeederId(feederCreatedResponse.getFeederId()))
                .feederName(feederCreatedResponse.getFeederName())
                .energyMeterNo(feederCreatedResponse.getEnergyMeterNo())
                .substationOfficeId(new OfficeId(feederCreatedResponse.getSubstationOfficeId()))
                .voltageLevel(feederCreatedResponse.getVoltageLevel())
                .feederType(feederCreatedResponse.getFeederType())
                .incomer11kVFeederId(new FeederId(feederCreatedResponse.getIncomer11kVFeederId()))
                .feedingPTRId(new FeederId(feederCreatedResponse.getFeedingPTRId()))
                .feeding33kVFeederId(new FeederId(feederCreatedResponse.getFeeding33kVFeederId()))
                .isCharged(feederCreatedResponse.isCharged())
                .isLoaded(feederCreatedResponse.isLoaded())
                .build();
    }

    public Feeder feederDtoToFeeder(FeederDTO feederDTO) {
        return Feeder.newBuilder()
                .feederId(new FeederId(feederDTO.getFeederId()))
                .feederName(feederDTO.getFeederName())
                .feederType(feederDTO.getFeederType())
                .energyMeterNo(feederDTO.getEnergyMeterNo())
                .voltageLevel(feederDTO.getVoltageLevel())
                .substationOfficeId(new OfficeId(feederDTO.getSubstationOfficeId()))
                .incomer11kVFeederId(new FeederId(feederDTO.getIncoming11kVFeeder().getFeederId()))
                .feedingPTRId(new FeederId(feederDTO.getFeedingPTR().getFeederId()))
                .feeding33kVFeederId(new FeederId(feederDTO.getFeeding33kVFeeder().getFeederId()))
                .isLoaded(feederDTO.getIsLoaded())
                .build();
    }

    public FeederDTO feederToFeederDto(Feeder feeder) {
        if (feeder != null) {
            Feeder incoming11kvFeeder = feeder.getIncomer11kVFeederId() == null ? null : feederRepository.findFeeder(feeder.getIncomer11kVFeederId().getValue()).get();
            Feeder feedingPTR = feeder.getFeedingPTRId() == null ? null : feederRepository.findFeeder(feeder.getFeedingPTRId().getValue()).get();
            Feeder feeding33kvFeeder = feeder.getFeeding33kVFeederId() == null ? null : feederRepository.findFeeder(feeder.getFeeding33kVFeederId().getValue()).get();
            return FeederDTO.builder()
                    .feederId(feeder.getId().getValue())
                    .feederName(feeder.getFeederName())
                    .feederType(feeder.getFeederType())
                    .voltageLevel(feeder.getVoltageLevel())
                    .energyMeterNo(feeder.getEnergyMeterNo())
                    .substationOfficeId(feeder.getSubstationOfficeId().getValue())
                    .incoming11kVFeeder(feederToFeederDto(incoming11kvFeeder))
                    .feedingPTR(feederToFeederDto(feedingPTR))
                    .feeding33kVFeeder(feederToFeederDto(feeding33kvFeeder))
                    .isCharged(feeder.getCharged())
                    .isLoaded(feeder.isLoaded())
                    .build();
        }
        return null;

    }
}
