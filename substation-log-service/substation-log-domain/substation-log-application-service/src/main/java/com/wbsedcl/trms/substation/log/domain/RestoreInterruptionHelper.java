package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.RestoreInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.entity.*;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionRestoredEvent;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionDomainException;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionNotFoundException;
import com.wbsedcl.trms.substation.log.domain.exception.UserNotFoundException;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class RestoreInterruptionHelper {

    private final SubstationLogRepository substationLogRepository;
    private final UserRepository userRepository;
    private final SubstationLogDomainService substationLogDomainService;

    private final FeederRepository feederRepository;

    public RestoreInterruptionHelper(SubstationLogRepository substationLogRepository,
                                     UserRepository userRepository,
                                     SubstationLogDomainService substationLogDomainService, FeederRepository feederRepository) {
        this.substationLogRepository = substationLogRepository;
        this.userRepository = userRepository;
        this.substationLogDomainService = substationLogDomainService;
        this.feederRepository = feederRepository;
    }

    @Transactional
    public InterruptionRestoredEvent updateInterruptionStatus(RestoreInterruptionCommand command) {
        //1.validate restored by user id.
        validateRestoredByUserId(command);
        //2.validate the reference id and find the interruption from repository.
        Interruption interruption = findInterruption(command);
        log.info("Found interruption : "+interruption);
        //3.validate the found interruption and check if it is already restored.
        validateFoundInterruptionStatus(interruption);
        //4.restore the interruption using domain logic
        InterruptionRestoredEvent interruptionRestoredEvent = substationLogDomainService.restoreInterruption(interruption, command.getEndDate(), command.getEndTime(), new UserId(command.getRestoredByUserId()), command.getInterruptionCause());
        //5.persist the updated object
        substationLogRepository.save(interruption);
        //6.update the feeder charging and loading status
        Feeder feeder = interruption.getFaultyFeeder();
        feeder.setCharged(true);
        feeder.setIsLoaded(!(feeder.getFeederType() == FeederType.INCOMING_33kV));

        //7. Update the tagging of incoming 33kV source, PTR and incomers
        if (command.get_33kVSourceChanged()) {
            log.info("Interruption with reference id {} restored with 33kV source {}", interruption.getId().getValue(), command.getRestoredBy33kVSourceId());
            feeder.setFeeding33kVFeederId(new FeederId(command.getRestoredBy33kVSourceId()));
        }
        if (command.getFeedingPTRChanged()) {
            log.info("Interruption with reference id {} restored with PTR {}", interruption.getId().getValue(), command.getRestoredByPTRId());
            feeder.setFeedingPTRId(new FeederId(command.getRestoredByPTRId()));
        }
        if (command.getFeedingIncomerChanged()) {
            log.info("Interruption with reference id {} restored with PTR {}", interruption.getId().getValue(), command.getRestoredByIncomerId());
            feeder.setIncomer11kVFeederId(new FeederId(command.getRestoredByIncomerId()));
        }
        feederRepository.saveFeeder(feeder);

        //8.log the operation
        log.info("Interruption with reference id {} restored", interruption.getId().getValue());
        //9.return the com.wbsedcl.hr.management.domain.event with the updated interruption
        return interruptionRestoredEvent;
    }

    private void validateFoundInterruptionStatus(Interruption interruption) {
        if (!interruption.getInterruptionStatus().equals(InterruptionStatus.NOT_RESTORED)){
            log.error("Interruption is not in correct state for restoration");
            throw new InterruptionDomainException("Interruption is not in correct state for restoration");
        }
    }

    private Interruption findInterruption(RestoreInterruptionCommand command) {
        UUID interruptionId = command.getInterruptionId();
        if (interruptionId == null) {
            log.error("Invalid null value interruption id submitted");
            throw new InterruptionNotFoundException("Please enter a valid non null interruption id");
        }
        Optional<Interruption> interruption = substationLogRepository.findInterruptionById(interruptionId.toString());
        if (interruption.isEmpty()) {
            log.error("Interruption with id {} does not exist", interruptionId);
            throw new InterruptionNotFoundException("Interruption with id "+interruptionId+" does not exist");
        }
        return interruption.get();
    }

    private void validateRestoredByUserId(RestoreInterruptionCommand command) {
        String restoredByUserId = command.getRestoredByUserId();
        if (restoredByUserId == null) {
            throw new UserNotFoundException("restoredByUserId retrieved from security context is null");
        }
        Optional<User> user = userRepository.findUser(restoredByUserId);
        if(user.isEmpty()) {
            log.error("AuthenticatedUser with id {} does not exist", restoredByUserId);
            throw new UserNotFoundException("AuthenticatedUser with id "+restoredByUserId+" does not exist");
        }
    }
}
