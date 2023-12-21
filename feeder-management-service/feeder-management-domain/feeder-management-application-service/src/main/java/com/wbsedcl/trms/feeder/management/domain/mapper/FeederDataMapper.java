package com.wbsedcl.trms.feeder.management.domain.mapper;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.feeder.management.domain.dto.create.CreateFeederCommand;
import com.wbsedcl.trms.feeder.management.domain.dto.message.FeederCreatedResponse;
import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import org.springframework.stereotype.Component;

@Component
public class FeederDataMapper {
    public Feeder createFeederCommandToFeeder(CreateFeederCommand command) {
       return Feeder.newBuilder()
                .feederId(new FeederId(command.getFeederId()))
                .feederText(command.getFeederText())
                .sourceSubstationOfficeId(command.getSourceSubstationOfficeId())
                .terminalSubstationOfficeId(command.getTerminalSubstationOfficeId())
                .ctRatioAtSource(command.getCtRatioAtSource())
                .ctRatioAtTerminal(command.getCtRatioAtTerminal())
                .energyMeterNoAtSource(command.getEnergyMeterNoAtSource())
                .energyMeterNoAtTerminal(command.getEnergyMeterNoAtTerminal())
                .feederType(command.getFeederType())
                .voltageLevel(command.getVoltageLevel())
                .isDedicatedBulkFeeder(command.getIsDedicatedBulkFeeder())
                .incomerCTRatio(command.getIncomerCTRatio())
                .multiplyingFactor(command.getMultiplyingFactor())
                .gisLength(command.getGisLength())
                .feedingPTRId(new FeederId(command.getFeedingPTRId()))
               .feeding33kVFeederId(new FeederId(command.getFeeding33kVFeederId()))
               .incomer11kVFeederId(new FeederId(command.getIncomer11kVFeederId()))
                .switchgearId(command.getSwitchgearId())
                .build();
    }

    public FeederCreatedResponse feederToFeederCreatedResponse(Feeder createdFeeder) {
        return FeederCreatedResponse.builder()
                .feederId(createdFeeder.getId().getValue())
                .sourceSubstationId(createdFeeder.getSourceSubstationOfficeId())
                .feederText(createdFeeder.getFeederText())
                .voltageLevel(createdFeeder.getVoltageLevel())
                .build();
    }
}


