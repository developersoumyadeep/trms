package com.wbsedcl.trms.substation.log.domain.mapper;


import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionResponse;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogSourceChangeOverInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.RestoreInterruptionResponse;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionType;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class InterruptionDataMapper {

    private final SubstationLogRepository substationLogRepository;
    private final FeederRepository feederRepository;

    public InterruptionDataMapper(SubstationLogRepository substationLogRepository, FeederRepository feederRepository) {
        this.substationLogRepository = substationLogRepository;
        this.feederRepository = feederRepository;
    }

    public LogInterruptionResponse interruptionToLogInterruptionResponse(Interruption interruption) {
        return LogInterruptionResponse.builder()
                .interruptionId(interruption.getId().getValue())
                .interruptionStatus(interruption.getInterruptionStatus())
                .message("Interruption successfully logged of type "+interruption.getInterruptionType())
                .interruptedFeederId(interruption.getFaultyFeeder().getId().getValue())
                .interruptedFeederIsCharged(interruption.getInterruptionStatus() == InterruptionStatus.RESTORED)
                .interruptedFeederIsLoaded(feederRepository.findFeeder(interruption.getFaultyFeeder().getId().getValue()).get().isLoaded())
                .build();
    }


    public Interruption logInterruptionCommandToInterruption(LogInterruptionCommand command) {
        log.info("Interruption being mapped from a command where restored by User id is {}",command.getRestoredByUserId());
        Feeder faultyFeeder = feederRepository.findFeeder(command.getFaultyFeederId()).get();
        return Interruption.newBuilder()
                .faultyFeeder(faultyFeeder)
                .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                .interruptionType(command.getInterruptionType())
                .faultNature(command.getFaultNature())
                .createdBy(new UserId(command.getCreatedByUserId()))
                .startDate(command.getStartDate())
                .startTime(command.getStartTime())
                .restoredBy(new UserId(command.getRestoredByUserId()))
                .interruptionStatus(command.getInterruptionStatus())
                .cause(command.getCause())
                .endDate(command.getEndDate())
                .endTime(command.getEndTime())
                .build();
    }

    public List<Interruption> logSourceChangeOverInterruptionCommandToInterruption(LogSourceChangeOverInterruptionCommand command) {
        //1. Get the 33kV feeder which is being unloaded
        String feederIdOfUnloaded33kVFeeder = command.getSourceChangeOverFromFeederId();
        //2. Get the affected feeders fed by affected PTRs
        List<String> affectedPTRIds = command.getAffectedPTRIds();
        log.info("logSourceChangeOverInterruptionCommandToInterruption: affected PTR ids"+ affectedPTRIds);
        List<Feeder> affectedPTRs = new ArrayList<>();
        List<Feeder> child11kVFeeders = new ArrayList<>();
        for (String ptrId : affectedPTRIds) {
            child11kVFeeders.addAll(substationLogRepository.getChildFeedersOfPTR(ptrId));
            affectedPTRs.add(feederRepository.findFeeder(ptrId).get());
        }
        log.info("logSourceChangeOverInterruptionCommandToInterruption: children of affected PTRs "+child11kVFeeders);
        //3. Build interruption for each of the affected 11kV feeders and add them to the list
        List<Interruption> interruptionsOfAffectedFeeders = new ArrayList<>();
        for (Feeder childFeeder : child11kVFeeders) {
            interruptionsOfAffectedFeeders.add(Interruption.newBuilder()
                    .faultyFeeder(childFeeder)
                    .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                    .interruptionType(InterruptionType.SOURCE_CHANGEOVER)
                    .interruptionStatus(InterruptionStatus.RESTORED)
                    .createdBy(new UserId(command.getCreatedByUserId()))
                    .startDate(command.getStartDate())
                    .startTime(command.getStartTime())
                    .endDate(command.getEndDate())
                    .endTime(command.getEndTime())
                    .restoredBy(new UserId(command.getRestoredByUserId()))
                    .sourceChangeOverFromFeederId(new FeederId(command.getSourceChangeOverFromFeederId()))
                    .sourceChangeOverToFeederId(new FeederId(command.getSourceChangeOverToFeederId()))
                    .build());
        }
        //4. Add interruption of affected PTRs to the output
        for (Feeder affectedPTR : affectedPTRs) {
            interruptionsOfAffectedFeeders.add(Interruption.newBuilder()
                    .faultyFeeder(affectedPTR)
                    .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                    .interruptionType(InterruptionType.SOURCE_CHANGEOVER)
                    .interruptionStatus(InterruptionStatus.RESTORED)
                    .createdBy(new UserId(command.getCreatedByUserId()))
                    .startDate(command.getStartDate())
                    .startTime(command.getStartTime())
                    .endDate(command.getEndDate())
                    .endTime(command.getEndTime())
                    .restoredBy(new UserId(command.getRestoredByUserId()))
                    .sourceChangeOverFromFeederId(new FeederId(command.getSourceChangeOverFromFeederId()))
                    .sourceChangeOverToFeederId(new FeederId(command.getSourceChangeOverToFeederId()))
                    .build());
        }
        log.info("logSourceChangeOverInterruptionCommandToInterruption: interruptions due to source change over :"+interruptionsOfAffectedFeeders);
        //4. Return the list
        return interruptionsOfAffectedFeeders;
    }

    public RestoreInterruptionResponse interruptionToRestoreInterruptionResponse(Interruption interruption) {
        return RestoreInterruptionResponse.builder()
                .interruptionId(interruption.getId().getValue())
                .interruptionStatus(interruption.getInterruptionStatus())
                .build();
    }

    public List<Interruption> logInterruptionCommandToMainPowerFailInterruptionList(LogInterruptionCommand command) {
        //1. Get the affected 33kV feeder
        String feederId = command.getFaultyFeederId();
        //2. Get the affected feeders fed by the faulty 33kV source
        List<Feeder> affectedFeeders = substationLogRepository.getChildFeedersOf33kVFeeder(feederId);
        //3. Build interruption for each of the affected feeders and add them to the list
        List<Interruption> interruptionsOfAffectedFeeders = new ArrayList<>();
        for (Feeder childFeeder : affectedFeeders) {
            interruptionsOfAffectedFeeders.add(Interruption.newBuilder()
                    .faultyFeeder(childFeeder)
                    .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                    .interruptionType(InterruptionType.MAIN_POWER_FAIL)
                    .faultNature(command.getFaultNature())
                    .interruptionStatus(InterruptionStatus.RESTORED)
                    .createdBy(new UserId(command.getCreatedByUserId()))
                    .startDate(command.getStartDate())
                    .startTime(command.getStartTime())
                    .endDate(command.getEndDate())
                    .endTime(command.getEndTime())
                    .restoredBy(new UserId(command.getRestoredByUserId()))
                    .build());
        }
        //4. Return the list
        return interruptionsOfAffectedFeeders;

    }
}
