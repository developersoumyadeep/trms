package com.wbsedcl.trms.substation.log.domain.mapper;


import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.*;
import com.wbsedcl.trms.substation.log.domain.dto.message.InterruptionDTO;
import com.wbsedcl.trms.substation.log.domain.entity.*;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .message("Interruption successfully logged of type " + interruption.getInterruptionType())
                .interruptedFeederId(interruption.getFaultyFeeder().getId().getValue())
                .interruptionType(interruption.getInterruptionType())
                .interruptionFaultNature(interruption.getFaultNature())
                .interruptionStartDate(interruption.getStartDate())
                .interruptionStartTime(interruption.getStartTime())
                .interruptedFeederIsCharged(interruption.getInterruptionStatus() == InterruptionStatus.RESTORED)
                .interruptedFeederIsLoaded(feederRepository.findFeeder(interruption.getFaultyFeeder().getId().getValue()).get().isLoaded())
                .build();
    }

    public Interruption logInterruptionCommandToInterruption(LogInterruptionCommand command) {
        log.info("Interruption being mapped from a command where restored by User id is {}", command.getRestoredByUserId());
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

    public InterruptionDTO interruptionToInterruptionDTO(Interruption interruption) {
        return new InterruptionDTO().builder()
                .interruptionId(interruption.getId().getValue().toString())
                .faultyFeederId(interruption.getFaultyFeeder().getId().getValue())
                .faultyFeederName(interruption.getFaultyFeeder().getFeederName())
                .faultNature(interruption.getFaultNature())
                .interruptionType(interruption.getInterruptionType())
                .interruptionStatus(interruption.getInterruptionStatus())
                .startDate(interruption.getStartDate())
                .startTime(interruption.getStartTime())
//                .interruptedFeederIsLoaded(interruption.getFaultyFeeder().getCharged())
//                .interruptedFeederIsLoaded(interruption.getFaultyFeeder().isLoaded())
                .build();
    }

    public List<Interruption> logSourceChangeOverInterruptionCommandToInterruption(LogSourceChangeOverInterruptionCommand command) {

        //Get the list of affected feeders fed by affected PTRs
        List<String> affectedPTRIds = command.getAffectedPTRIds();
        log.info("logSourceChangeOverInterruptionCommandToInterruption: affected PTR ids" + affectedPTRIds);
        List<Feeder> affectedPTRs = new ArrayList<>();

        //Initialize the list of all 11kV feeders fed by the affected PTRs (A)
        List<Feeder> child11kVFeeders = new ArrayList<>();
        for (String ptrId : affectedPTRIds) {
            child11kVFeeders.addAll(substationLogRepository.getChildFeedersOfPTR(ptrId));
            affectedPTRs.add(feederRepository.findFeeder(ptrId).get());
        }

        //Initialize the list of interruptions of affected feeders
        List<Interruption> interruptionsOfAffectedFeeders = new ArrayList<>();
        //Initialize the list of unrestored feeders (B) that could not be charged during substation normalization due to breaker issues etc.
        List<Feeder> unrestoredFeeders = new ArrayList<>();

        if (command.getUnrestoredFeederIds().size() > 0) {
            //If there are unrestored feeder ids in the command then add their interruptions to the interruption list
            log.info("Unrestored feeders detected in the main power fail command");
            for (String feederId : command.getUnrestoredFeederIds()) {
                unrestoredFeeders.add(feederRepository.findFeeder(feederId).get());
            }
            //7. Construct the interruptions of (B) feeders from the command and add them to the interruptions list
            for (Feeder unrestoredFeeder : unrestoredFeeders) {
                interruptionsOfAffectedFeeders.add(Interruption.newBuilder()
                        .faultyFeeder(unrestoredFeeder)
                        .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                        .interruptionType(InterruptionType.SOURCE_CHANGEOVER)
                        .interruptionStatus(InterruptionStatus.NOT_RESTORED)
                        .createdBy(new UserId(command.getCreatedByUserId()))
                        .startDate(command.getStartDate())
                        .startTime(command.getStartTime())
                        .sourceChangeOverToFeederId(new FeederId(command.getSourceChangeOverToFeederId()))
                        .build());
            }
        }

        //Construct the interruptions of (A-B) feeders from the command and add them to the interruptions list
        List<Feeder> restoredFeeders = new ArrayList<>(child11kVFeeders);
        restoredFeeders.removeAll(unrestoredFeeders);
        log.info("Restored feeders after removal of unrestored feeders from affected feeders are " + restoredFeeders);
        for (Feeder restoredFeeder : restoredFeeders) {
            interruptionsOfAffectedFeeders.add(Interruption.newBuilder()
                    .faultyFeeder(restoredFeeder)
                    .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                    .interruptionType(InterruptionType.SOURCE_CHANGEOVER)
                    .interruptionStatus(InterruptionStatus.RESTORED)
                    .createdBy(new UserId(command.getCreatedByUserId()))
                    .startDate(command.getStartDate())
                    .startTime(command.getStartTime())
                    .endDate(command.getEndDate())
                    .endTime(command.getEndTime())
                    .sourceChangeOverToFeederId(new FeederId(command.getSourceChangeOverToFeederId()))
                    .restoredBy(new UserId(command.getCreatedByUserId()))
                    .build());
        }

        //Add interruptions of the affected PTRs to the interruption list
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
        return interruptionsOfAffectedFeeders;
    }

    public RestoreInterruptionResponse interruptionToRestoreInterruptionResponse(Interruption interruption) {
        return RestoreInterruptionResponse.builder()
                .interruptionId(interruption.getId().getValue())
                .interruptionStatus(interruption.getInterruptionStatus())
                .build();
    }

    public Change33kVIncomingSourceCommand logSourceChangeOverInterruptionCommandToChange33kVIncomingSourceCommand(LogSourceChangeOverInterruptionCommand command) {
        String sourceChangeOverFromFeederId = command.getSourceChangeOverFromFeederId();
        String sourceChangeOverToFeederId = command.getSourceChangeOverToFeederId();
        List<String> affectedPTRIds = command.getAffectedPTRIds();
        LocalDate startDate = command.getStartDate();
        LocalTime startTime = command.getStartTime();
        LocalDate endDate = command.getEndDate();
        LocalTime endTime = command.getEndTime();
        Change33kVSourceCommandContext context = Change33kVSourceCommandContext.NORMAL;
        return new Change33kVIncomingSourceCommand(sourceChangeOverFromFeederId, sourceChangeOverToFeederId, affectedPTRIds, startDate, startTime, endDate, endTime, context);
    }

    public Change33kVIncomingSourceCommand logMainPowerFailCommandToChange33kVIncomingSourceCommand(MainPowerFailCommand command) {
        if (command.getSourceChangeOverToFeederId() != null) {
            String sourceChangeOverFromFeederId = command.getFaulty33kVSourceFeederId();
            String sourceChangeOverToFeederId = command.getSourceChangeOverToFeederId();
            List<String> affectedPTRIds = substationLogRepository
                    .getChildFeedersOf33kVFeeder(sourceChangeOverFromFeederId)
                    .stream()
                    .filter(feeder -> feeder.getFeederType() == FeederType.PTR_BAY)
                    .map(feeder -> feeder.getId().getValue())
                    .toList();
            log.info("affected PTR ids {}", affectedPTRIds);
            LocalDate startDate = command.getStartDate();
            LocalTime startTime = command.getStartTime();
            LocalDate endDate = command.getOutgoingFeederInterruptionEndDate();
            LocalTime endTime = command.getOutgoingFeederInterruptionEndTime();
            Change33kVSourceCommandContext context = Change33kVSourceCommandContext.INCOMING_SOURCE_FAILED;
            return new Change33kVIncomingSourceCommand(sourceChangeOverFromFeederId, sourceChangeOverToFeederId, affectedPTRIds, startDate, startTime, endDate, endTime, context);
        }
        return null;
    }

    public List<Interruption> logMainPowerFailCommandToMainPowerFailInterruptionList(MainPowerFailCommand command) {
        //1. Create the empty interruption list
        List<Interruption> interruptionsOfAffectedFeeders = new ArrayList<>();
        //2. Get the faulty 33kV feeder from the command
        String faulty33kVSourceFeederId = command.getFaulty33kVSourceFeederId();
        Optional<Feeder> feederOptional = feederRepository.findFeeder(faulty33kVSourceFeederId);
        if (!feederOptional.isEmpty()) {
            //3. Construct the interruption of the faulty 33kV feeder and add it to the interruption list
            interruptionsOfAffectedFeeders.add(Interruption.newBuilder()
                    .faultyFeeder(feederOptional.get())
                    .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                    .interruptionType(command.get_33kVInterruptionType())
                    .faultNature(command.get_33kVFaultNature())
                    .interruptionStatus(command.get_33kVSourceInterruptionStatus())
                    .createdBy(new UserId(command.getCreatedByUserId()))
                    .startDate(command.getStartDate())
                    .startTime(command.getStartTime())
                    .endDate(command.get_33kVInterruptionEndDate())
                    .endTime(command.get_33kVInterruptionEndTime())
                    .restoredBy(command.get_33kVSourceRestoredByUserId() == null ? null : new UserId(command.get_33kVSourceRestoredByUserId()))
                    .build());
        }

        //5. Get the affected feeders fed by the faulty 33kV source (A)
        List<Feeder> affectedFeeders = substationLogRepository.getChildFeedersOf33kVFeeder(faulty33kVSourceFeederId);


        //6. Check if substation outage is being logged
        if (command.get_33kVSourceInterruptionStatus() == InterruptionStatus.NOT_RESTORED && command.getSourceChangeOverToFeederId() == null) {
            //Build the interruptions of the affected feeders and set Interruption status to unrestored
            log.info("Substation outage detected");
            for (Feeder affectedFeeder : affectedFeeders) {
                interruptionsOfAffectedFeeders.add(Interruption.newBuilder()
                        .faultyFeeder(affectedFeeder)
                        .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                        .interruptionType(InterruptionType.MAIN_POWER_FAIL)
                        .interruptionStatus(InterruptionStatus.NOT_RESTORED)
                        .createdBy(new UserId(command.getCreatedByUserId()))
                        .startDate(command.getStartDate())
                        .startTime(command.getStartTime())
                        .build());
            }
        } else {
            //If there is no substation outage then check if there are any unrestored feeders due to issues during substation normalization (switchgear issue etc.)
            //6. Get the unrestored feeders from the command (B)
            List<Feeder> unrestoredFeeders = new ArrayList<>();
            if (command.getUnrestoredFeederIds().size() > 0) {
                log.info("Unrestored feeders detected in the main power fail command");
                for (String feederId : command.getUnrestoredFeederIds()) {
                    unrestoredFeeders.add(feederRepository.findFeeder(feederId).get());
                }
                //7. Construct the interruptions of (B) feeders from the command and add them to the interruptions list
                for (Feeder unrestoredFeeder : unrestoredFeeders) {
                    interruptionsOfAffectedFeeders.add(Interruption.newBuilder()
                            .faultyFeeder(unrestoredFeeder)
                            .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                            .interruptionType(InterruptionType.MAIN_POWER_FAIL)
                            .interruptionStatus(InterruptionStatus.NOT_RESTORED)
                            .createdBy(new UserId(command.getCreatedByUserId()))
                            .startDate(command.getStartDate())
                            .startTime(command.getStartTime())
                            .build());
                }
            }
            //9. Construct the interruptions of (A-B) feeders from the command and add them to the interruptions list
            List<Feeder> restoredFeeders = new ArrayList<>(affectedFeeders);
            restoredFeeders.removeAll(unrestoredFeeders);
            log.info("Restored feeders after removal of unrestored feeders from affected feeders are " + restoredFeeders);
            for (Feeder restoredFeeder : restoredFeeders) {
                interruptionsOfAffectedFeeders.add(Interruption.newBuilder()
                        .faultyFeeder(restoredFeeder)
                        .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                        .interruptionType(InterruptionType.MAIN_POWER_FAIL)
                        .interruptionStatus(InterruptionStatus.RESTORED)
                        .createdBy(new UserId(command.getCreatedByUserId()))
                        .startDate(command.getStartDate())
                        .startTime(command.getStartTime())
                        .endDate(command.getOutgoingFeederInterruptionEndDate())
                        .endTime(command.getOutgoingFeederInterruptionEndTime())
                        .restoredBy(new UserId(command.getCreatedByUserId()))
                        .build());
            }
        }
        //11. Return the interruption list
        return interruptionsOfAffectedFeeders;
    }
}
