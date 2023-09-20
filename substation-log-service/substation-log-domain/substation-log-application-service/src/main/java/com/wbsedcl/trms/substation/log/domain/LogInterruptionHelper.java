package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogSourceChangeOverInterruptionCommand;
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
        //1. Validate the restoredBy user
        validateRestoredByUserId(command);
        //2. Get domain object from command
        Interruption interruption = interruptionDataMapper.logInterruptionCommandToInterruption(command);
        //***** if the restoredBy user is null the set the restoredBy user to "XXXXXXXX" to avoid TransientPropertyValueException when saving interruption *****//
        interruption.setRestoredBy(new UserId("XXXXXXXX"));
        //3. Validate and initiate an interruption (domain logic)
        InterruptionLoggedEvent interruptionLoggedEvent = substationLogDomainService.validateAndInitiateInterruption(interruption);
        log.info("interruption.creationTimeStamp():{}",interruption.getCreationTimeStamp());
        //4. Save the interruption using repository
        substationLogRepository.save(interruption);
        //5. Log the operation
        log.info("Interruption saved with id {}", interruption.getId().getValue());
        //6. Return the event with the saved interruption
        return interruptionLoggedEvent;
    }

    @Transactional
    public InterruptionLoggedEvent persistSourceChangeOverInterruption(LogSourceChangeOverInterruptionCommand command) {
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
        InterruptionLoggedEvent sourceChangeOverInterruptionLoggedEvent = null;
        //5. Validate and initiate the interruption
        for (Interruption interruptionOfAffectedFeeder :
                interruptionsOfAffectedFeeders) {
            sourceChangeOverInterruptionLoggedEvent = substationLogDomainService.validateAndInitiateInterruption(interruptionOfAffectedFeeder);
            //6. Save interruption of all 11kV feeders and PTRs using repository
            substationLogRepository.save(interruptionOfAffectedFeeder);
            feederRepository.updateFeeding33kVFeeder(interruptionOfAffectedFeeder.getFaultyFeederId().getValue(), interruptionOfAffectedFeeder.getSourceChangeOverToFeederId().getValue());
        }
        //7.  a. Update the feeder_materialized_view table to reflect the modified 33kV source and loading status
        //7.   b. Update the feeder_loading_history table
        changeIncoming33kVSource(command);
        //7. Log the operation
        log.info("Source change over interruption logged at {}",sourceChangeOverInterruptionLoggedEvent.getCreatedAt());
        //8. Return the event
        return sourceChangeOverInterruptionLoggedEvent;
    }

    private void changeIncoming33kVSource(LogSourceChangeOverInterruptionCommand command) {
        String sourceChangeOverFromFeederId = command.getSourceChangeOverFromFeederId();
        String sourceChangeOverToFeederId = command.getSourceChangeOverToFeederId();
        Feeder oldIncomingSourceFeeder = feederRepository.findFeeder(sourceChangeOverFromFeederId).get();
        Feeder newIncomingSourceFeeder = feederRepository.findFeeder(sourceChangeOverToFeederId).get();
        if (oldIncomingSourceFeeder.isLoaded()) {
            oldIncomingSourceFeeder.setIsLoaded(false);
            feederRepository.saveFeeder(oldIncomingSourceFeeder);
            List<Feeder> feeders = substationLogRepository.getChildFeedersOf33kVFeeder(sourceChangeOverFromFeederId);
            for (Feeder feeder : feeders){
                feeder.setFeeding33kVFeederId(new FeederId(sourceChangeOverToFeederId));
                feederRepository.saveFeeder(feeder);
            }
        }
        log.info("old source change over feeder id {}", sourceChangeOverFromFeederId );
        FeederLoadingHistory oldIncomingSourceFeederLoadingHistory = feederRepository.getOpenLoadingHistoryByFeederId(sourceChangeOverFromFeederId);
        log.info("source change over from feeder id {} ", oldIncomingSourceFeederLoadingHistory.getFeederId());
        log.info("oldFeederLoadingHistory.loadedFromDate() : {}", oldIncomingSourceFeederLoadingHistory.getLoadedFromDate());
        oldIncomingSourceFeederLoadingHistory.setLoadedToDate(command.getStartDate());
        oldIncomingSourceFeederLoadingHistory.setLoadedToTime(command.getStartTime());
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
                log.error("User with id {} does not exist", restoredByUserId);
                throw new InterruptionValidationException("User with id "+restoredByUserId+" does not exist");
            }
        }
    }

    private void validateRestoreByUserId(LogSourceChangeOverInterruptionCommand command) {
        String createdByUserId = command.getCreatedByUserId();
        String restoredByUserId = command.getRestoredByUserId();
        if (!restoredByUserId.equals(createdByUserId)) {
            log.error("restoredByUserId and createdByUserId do not match for source change over interruption log request");
            throw new InterruptionValidationException("Interruption of type 'Source Change-over' must have same created-by user id and restored-by user id");
        }
        if (restoredByUserId != null) {
            Optional<User> user = userRepository.findUser(restoredByUserId);
            if(user.isEmpty()) {
                log.error("User with id {} does not exist", restoredByUserId);
                throw new InterruptionValidationException("User with id "+restoredByUserId+" does not exist");
            }
        }

    }

    public InterruptionLoggedEvent persistMainPowerFailInterruption(LogInterruptionCommand command) {
        //1. Validate the interruption type
        if (command.getInterruptionType() != InterruptionType.MAIN_POWER_FAIL) {
            log.error("Invalid interruption type {} for main power fail interruption log request", command.getInterruptionType());
            throw new InterruptionValidationException("Invalid interruption type for Main power fail interruption log request");
        }
        //2. Validate the interruption status
        if (command.getInterruptionStatus() != InterruptionStatus.RESTORED) {
            log.error("Invalid interruption status {} for main power fail interruption log request", command.getInterruptionStatus());
            throw new InterruptionValidationException("Invalid interruption status for main power fail interruption log request");
        }
        //3. Validate the restoredBy user id
        validateRestoredByUserId(command);
        //4. Get the domain object from the command
        List<Interruption> interruptionsOfAffectedFeeders = interruptionDataMapper.logInterruptionCommandToMainPowerFailInterruptionList(command);
        InterruptionLoggedEvent mainPowerFailedInterruptionLogEvent = null;
        for (Interruption interruption : interruptionsOfAffectedFeeders) {
            //5. Validate and initiate the interruption
             mainPowerFailedInterruptionLogEvent = substationLogDomainService.validateAndInitiateInterruption(interruption);
             //6. Save the interruption
             substationLogRepository.save(interruption);
        }
        //7. Log the info
        log.info("Main power failed interruption logged at {}", mainPowerFailedInterruptionLogEvent.getCreatedAt());
        //8. Return the event
        return mainPowerFailedInterruptionLogEvent;
    }
}
