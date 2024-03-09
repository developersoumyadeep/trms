package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.*;
import com.wbsedcl.trms.substation.log.domain.entity.*;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionValidationException;
import com.wbsedcl.trms.substation.log.domain.mapper.InterruptionDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class LogInterruptionHelper {

    private final SubstationLogDomainService substationLogDomainService;
    private final SubstationLogRepository substationLogRepository;
    private final UserRepository userRepository;

    private final FeederRepository feederRepository;
    private final InterruptionDataMapper interruptionDataMapper;

    public LogInterruptionHelper(SubstationLogDomainService substationLogDomainService,
                                 SubstationLogRepository substationLogRepository,
                                 UserRepository userRepository,
                                 FeederRepository feederRepository, InterruptionDataMapper interruptionDataMapper) {
        this.substationLogDomainService = substationLogDomainService;
        this.substationLogRepository = substationLogRepository;
        this.userRepository = userRepository;
        this.feederRepository = feederRepository;
        this.interruptionDataMapper = interruptionDataMapper;
    }

    @Transactional
    public InterruptionLoggedEvent persistInterruption(LogInterruptionCommand command) {
        log.info("persisting interruption ...");
        //1. Validate the restoredBy user
        validateRestoredByUserId(command);
        //2. Get domain object from command
        Interruption interruption = interruptionDataMapper.logInterruptionCommandToInterruption(command);
        //***** if the restoredBy user is null the set the restoredBy user to "XXXXXXXX" to avoid TransientPropertyValueException when saving interruption *****//
        if (interruption.getRestoredBy().getValue() == null) {
            interruption.setRestoredBy(new UserId("XXXXXXXX"));
        } else {
            interruption.setRestorationTimeStamp(LocalDateTime.now());
        }
        //3. Validate and initiate an interruption (domain logic)
        InterruptionLoggedEvent interruptionLoggedEvent = substationLogDomainService.validateAndInitiateInterruption(interruption);
        log.info("interruption.creationTimeStamp():{}",interruption.getCreationTimeStamp());
        //4. Save the interruption using repository
        substationLogRepository.save(interruption);
        //5. Log the operation
        log.info("Interruption saved with id {}", interruption.getId().getValue());
        //6. Update the feeder loading and charging status
        Feeder feeder = feederRepository.findFeeder(interruption.getFaultyFeeder().getId().getValue()).get();
        feeder.setCharged(interruption.getInterruptionStatus() == InterruptionStatus.RESTORED);
        feeder.setIsLoaded(interruption.getInterruptionStatus() == InterruptionStatus.RESTORED);
        feederRepository.saveFeeder(feeder);
        //6. Return the event with the saved interruption
        return interruptionLoggedEvent;
    }

    @Transactional
    public List<InterruptionLoggedEvent> persistSourceChangeOverInterruption(LogSourceChangeOverInterruptionCommand command) {
        //1. Validate the interruption type
        if (command.getInterruptionType() != InterruptionType.SOURCE_CHANGEOVER) {
            throw new InterruptionValidationException("Incorrect interruption type "+command.getInterruptionType()+" for the source change over request type");
        }
        //2. Validate the interruption status
        if (command.getInterruptionStatus() != InterruptionStatus.RESTORED) {
            throw new InterruptionValidationException("A request of type source changeover must have restored interruption status");
        }
        //3. Validate the restoredBy user id
        validateRestoreByUserId(command);
        //4. Get domain object from  command
        List<Interruption> interruptionsOfAffectedFeeders = interruptionDataMapper.logSourceChangeOverInterruptionCommandToInterruption(command);
        log.info("persistSourceChangeOverInterruption: interruptions of affected feeders "+interruptionsOfAffectedFeeders);
        List<InterruptionLoggedEvent> sourceChangeOverInterruptionLoggedEvents = new ArrayList<>();
        //5. Validate and initiate the interruption
        for (Interruption interruptionOfAffectedFeeder :
                interruptionsOfAffectedFeeders) {
            sourceChangeOverInterruptionLoggedEvents.add(substationLogDomainService.validateAndInitiateInterruption(interruptionOfAffectedFeeder));
            //6. Save interruption of all 11kV feeders and PTRs using repository
            substationLogRepository.save(interruptionOfAffectedFeeder);
            feederRepository.updateFeeding33kVFeeder(interruptionOfAffectedFeeder.getFaultyFeeder().getId().getValue(), interruptionOfAffectedFeeder.getSourceChangeOverToFeederId().getValue());
            //7. Update the feeder charging and loading status
            Feeder faultyFeeder =  interruptionOfAffectedFeeder.getFaultyFeeder();
            faultyFeeder.setCharged(interruptionOfAffectedFeeder.getInterruptionStatus() == InterruptionStatus.RESTORED);
            faultyFeeder.setIsLoaded(interruptionOfAffectedFeeder.getInterruptionStatus() == InterruptionStatus.RESTORED);
            feederRepository.saveFeeder(faultyFeeder);
        }
        //7.  a. Update the record of each of the affected feeders and PTRs to reflect the modified feeding 33kV source and loading status
        //7.   b. Update the feeder_loading_history table
        Change33kVIncomingSourceCommand change33kVIncomingSourceCommand = interruptionDataMapper.logSourceChangeOverInterruptionCommandToChange33kVIncomingSourceCommand(command);
        changeIncoming33kVSource(change33kVIncomingSourceCommand);
        //8. Log the operation
        log.info("Source change over interruption logged at {}",sourceChangeOverInterruptionLoggedEvents.get(0).getCreatedAt());
        //9. Return the event
        return sourceChangeOverInterruptionLoggedEvents;
    }

    private void changeIncoming33kVSource(Change33kVIncomingSourceCommand command) {
        String sourceChangeOverFromFeederId = command.getSourceChangeOverFromFeederId();
        String sourceChangeOverToFeederId = command.getSourceChangeOverToFeederId();

        List<String> affectedPTRIds = command.getAffectedPTRIds();
        List<Feeder> affectedPTRs = affectedPTRIds.stream().map(ptrId -> feederRepository.findFeeder(ptrId).get()).toList();
        Feeder oldIncomingSourceFeeder = feederRepository.findFeeder(sourceChangeOverFromFeederId).get();
        Feeder newIncomingSourceFeeder = feederRepository.findFeeder(sourceChangeOverToFeederId).get();

        //Get the child 11kV feeders of affected PTRs
        List<Feeder> childrenOfAffectedPTRs = new ArrayList<>();
        for (String affectedPTRId : affectedPTRIds) {
            childrenOfAffectedPTRs.addAll(substationLogRepository.getChildFeedersOfPTR(affectedPTRId));
        }

        //Set the feeding 33kV source of the child 11kV feeders to the new source
        for (Feeder affectedChildFeeder : childrenOfAffectedPTRs) {
            affectedChildFeeder.setFeeding33kVFeederId(new FeederId(sourceChangeOverToFeederId));
            feederRepository.saveFeeder(affectedChildFeeder);
        }

        //Set the feeding 33kV source of the affected PTRs
        for (Feeder affectedPTR : affectedPTRs) {
            affectedPTR.setFeeding33kVFeederId(new FeederId(sourceChangeOverToFeederId));
            feederRepository.saveFeeder(affectedPTR);
        }

        //Set the loading status of the old 33kV source
        if (substationLogRepository.getChildFeedersOf33kVFeeder(oldIncomingSourceFeeder.getId().getValue()).isEmpty()) {
            oldIncomingSourceFeeder.setIsLoaded(false);
            //If the context of the change over is incoming source fail then set the isCharged to false for the old incoming source
            if (command.getContext() == Change33kVSourceCommandContext.INCOMING_SOURCE_FAILED) {
                oldIncomingSourceFeeder.setCharged(false);
            }
            feederRepository.saveFeeder(oldIncomingSourceFeeder);
        }

        log.info("old source change over feeder id {}", sourceChangeOverFromFeederId );
        FeederLoadingHistory oldIncomingSourceFeederLoadingHistory = feederRepository.getOpenLoadingHistoryByFeederId(sourceChangeOverFromFeederId);
        log.info("source change over from feeder id {} ", oldIncomingSourceFeederLoadingHistory.getFeederId());
        log.info("oldFeederLoadingHistory.loadedFromDate() : {}", oldIncomingSourceFeederLoadingHistory.getLoadedFromDate());
        if (!oldIncomingSourceFeeder.isLoaded()) {
            oldIncomingSourceFeederLoadingHistory.setLoadedToDate(command.getStartDate());
            oldIncomingSourceFeederLoadingHistory.setLoadedToTime(command.getStartTime());
        }

        feederRepository.saveLoadingHistory(oldIncomingSourceFeederLoadingHistory);
        FeederLoadingHistory newIncomingSourceFeederLoadingHistory = null;
        log.info("newIncomingSourceFeeder.getId().getValue() : {}", newIncomingSourceFeeder.getId().getValue());
        log.info("newIncomingSourceFeeder.isLoaded() : {}", newIncomingSourceFeeder.isLoaded());
        if (!newIncomingSourceFeeder.isLoaded()) {
            log.info("Creating new loading history log for feeder {} ", newIncomingSourceFeeder.getId().getValue());
            newIncomingSourceFeederLoadingHistory = FeederLoadingHistory.builder()
                    .feederId(sourceChangeOverToFeederId)
                    .loadedFromDate(command.getEndDate())
                    .loadedFromTime(command.getEndTime())
                    .build();
            feederRepository.saveLoadingHistory(newIncomingSourceFeederLoadingHistory);
            newIncomingSourceFeeder.setIsLoaded(true);
            feederRepository.saveFeeder(newIncomingSourceFeeder);
        }
    }


    private void validateRestoredByUserId(LogInterruptionCommand command) {
        log.info("Validating restored by user id");
        String createdByUserId = command.getCreatedByUserId();
        String restoredByUserId = command.getRestoredByUserId();
        InterruptionStatus interruptionStatus = command.getInterruptionStatus();
        InterruptionType interruptionType = command.getInterruptionType();
        if (interruptionStatus.equals(InterruptionStatus.NOT_RESTORED) && restoredByUserId != null) {
            throw new InterruptionValidationException("Interruption which is not restored can not have a restored-by user id ");
        }
        if (interruptionStatus.equals(InterruptionStatus.RESTORED) && restoredByUserId == null) {
            throw new InterruptionValidationException("Restored interruption must have a non-null restored-by user id");
        }
        if ((interruptionType.equals(InterruptionType.TRANSIENT_TRIPPING) || interruptionType.equals(InterruptionType.MAIN_POWER_FAIL))
                    && interruptionStatus.equals(InterruptionStatus.RESTORED)){
            if (!restoredByUserId.equals(createdByUserId)) {
                throw new InterruptionValidationException("Interruption of type 'Transient Tripping' or 'Source Change-over' must have same created-by user id and restored-by user id");
            }
        }
        if (restoredByUserId != null) {
            Optional<User> user = userRepository.findUser(restoredByUserId);
            if(user.isEmpty()) {
                log.error("AuthenticatedUser with id {} does not exist", restoredByUserId);
                throw new InterruptionValidationException("AuthenticatedUser with id "+restoredByUserId+" does not exist");
            }
        }
    }

    private void validateRestoreByUserId(LogSourceChangeOverInterruptionCommand command) {
        String restoredByUserId = command.getRestoredByUserId();
        if (!restoredByUserId.equals(command.getCreatedByUserId())) {
            log.error("restoredByUserId and createdByUserId do not match for source change over interruption log request");
            throw new InterruptionValidationException("Interruption of type 'Source Change-over' must have same created-by user id and restored-by user id");
        }
        if (restoredByUserId != null) {
            Optional<User> user = userRepository.findUser(restoredByUserId);
            if(user.isEmpty()) {
                log.error("AuthenticatedUser with id {} does not exist", restoredByUserId);
                throw new InterruptionValidationException("AuthenticatedUser with id "+restoredByUserId+" does not exist");
            }
        }

    }

    private void validateRestoredByUserId(MainPowerFailCommand command) {
        String _33kVSourceRestoredByUserId = command.get_33kVSourceRestoredByUserId();
        String outgoingFeederRestoredByUserId = command.getOutgoingFeederRestoredByUserId();
        if (command.get_33kVSourceInterruptionStatus() == InterruptionStatus.RESTORED) {
            if (_33kVSourceRestoredByUserId == null) {
                throw new InterruptionValidationException("restoredByUserId must be present for transient tripping of loaded 33kV source");
            }
        } else {
            if (command.getSourceChangeOverToFeederId() == null) {
                if (outgoingFeederRestoredByUserId != null) {
                    throw new InterruptionValidationException("restoredByUserId must be not present for outgoing feeders in case of a substation outage");
                }
            }
        }

    }

    @Transactional
    public List<InterruptionLoggedEvent> persistMainPowerFailInterruption(MainPowerFailCommand command) {
        //3. Validate the restoredBy user id
        validateRestoredByUserId(command);
        //4. Get the domain object from the command
        List<Interruption> interruptionsOfAffectedFeeders = interruptionDataMapper.logMainPowerFailCommandToMainPowerFailInterruptionList(command);
        List<InterruptionLoggedEvent> mainPowerFailedInterruptionLogEvents = new ArrayList<>();
        for (Interruption interruption : interruptionsOfAffectedFeeders) {
             //5. Validate and initiate the interruption
             mainPowerFailedInterruptionLogEvents.add(substationLogDomainService.validateAndInitiateInterruption(interruption));
             //6. Save the interruption
             substationLogRepository.save(interruption);
             //7. Update the feeder charging and loading status
             Feeder faultyFeeder =  interruption.getFaultyFeeder();
            faultyFeeder.setCharged(interruption.getInterruptionStatus() == InterruptionStatus.RESTORED);
            faultyFeeder.setIsLoaded(interruption.getInterruptionStatus() == InterruptionStatus.RESTORED);
            feederRepository.saveFeeder(faultyFeeder);
        }
        //7. If source change over is needed then execute the below function
        if (command.getSourceChangeOverToFeederId() !=null) {
            changeIncoming33kVSource(interruptionDataMapper.logMainPowerFailCommandToChange33kVIncomingSourceCommand(command));
        }
        //8. Log the info
        log.info("Main power failed interruption logged at {}", mainPowerFailedInterruptionLogEvents.get(0).getCreatedAt());
        //9. Return the event
        return mainPowerFailedInterruptionLogEvents;
    }
}
