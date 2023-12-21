package com.wbsedcl.trms.feeder.management.dataaccess.feeder.mapper;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.feeder.management.dataaccess.feeder.entity.FeederEntity;
import com.wbsedcl.trms.feeder.management.dataaccess.office.entity.OfficeEntity;
import com.wbsedcl.trms.feeder.management.domain.entity.Feeder;
import com.wbsedcl.trms.feeder.management.domain.entity.Office;
import org.springframework.stereotype.Component;

@Component
public class FeederDataAccessMapper {


    public Feeder feederEntityToFeeder(FeederEntity feederEntity) {
        return Feeder.newBuilder()
                .feederId(new FeederId(feederEntity.getFeederId()))
                .feederText(feederEntity.getFeederText())
                .sourceSubstationOfficeId(feederEntity.getSourceSubstationOffice().getOfficeId())
                .terminalSubstationOfficeId(feederEntity.getTerminalSubstationOffice() == null ? null : feederEntity.getTerminalSubstationOffice().getOfficeId())
                .energyMeterNoAtSource(feederEntity.getEnergyMeterNoAtSource())
                .energyMeterNoAtTerminal(feederEntity.getEnergyMeterNoAtTerminal())
                .ctRatioAtSource(feederEntity.getCtRatioAtSource())
                .ctRatioAtTerminal(feederEntity.getCtRatioAtTerminal())
                .feederType(feederEntity.getFeederType())
                .voltageLevel(feederEntity.getVoltageLevel())
                .gisLength(feederEntity.getGisLength())
                .switchgearId(feederEntity.getSwitchgearId())
                .multiplyingFactor(feederEntity.getMultiplyingFactor())
                .feedingPTRId(new FeederId(feederEntity.getFeedingPTRId()))
                .isDedicatedBulkFeeder(feederEntity.getIsDedicatedBulkFeeder())
                .incomerCTRatio(feederEntity.getIncomerCtRatio())
                .incomer11kVFeederId(new FeederId(feederEntity.getIncomer11kVFeederId()))
                .feedingPTRId(new FeederId(feederEntity.getFeedingPTRId()))
                .feeding33kVFeederId(new FeederId(feederEntity.getFeeding33kVFeederId()))
                .build();
    }

    public FeederEntity feederToFeederEntity(Feeder feeder) {
        OfficeEntity sourceSubstationOffice = OfficeEntity.builder().officeId(feeder.getSourceSubstationOfficeId()).build();
        OfficeEntity terminalSubstationOffice = feeder.getTerminalSubstationOfficeId() == null ? null : OfficeEntity.builder().officeId(feeder.getTerminalSubstationOfficeId()).build();
        return FeederEntity.builder()
                .feederId(feeder.getId().getValue())
                .feederText(feeder.getFeederText())
                .sourceSubstationOffice(sourceSubstationOffice)
                .terminalSubstationOffice(terminalSubstationOffice)
                .energyMeterNoAtSource(feeder.getEnergyMeterNoAtSource())
                .energyMeterNoAtTerminal(feeder.getEnergyMeterNoAtTerminal())
                .ctRatioAtSource(feeder.getCtRatioAtSource())
                .ctRatioAtTerminal(feeder.getCtRatioAtTerminal())
                .feederType(feeder.getFeederType())
                .voltageLevel(feeder.getVoltageLevel())
                .gisLength(feeder.getGisLength())
                .switchgearId(feeder.getSwitchgearId())
                .multiplyingFactor(feeder.getMultiplyingFactor())
                .feedingPTRId(feeder.getFeedingPTRId().getValue())
                .isDedicatedBulkFeeder(feeder.isDedicatedBulkFeeder())
                .incomerCtRatio(feeder.getIncomerCTRatio())
                .incomer11kVFeederId(feeder.getIncomer11kVFeederId().getValue())
                .feedingPTRId(feeder.getFeedingPTRId().getValue())
                .feeding33kVFeederId(feeder.getFeeding33kVFeederId().getValue())
                .build();
    }
}

