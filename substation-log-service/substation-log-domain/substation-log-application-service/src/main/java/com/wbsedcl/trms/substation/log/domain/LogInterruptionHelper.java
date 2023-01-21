package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.entity.*;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionDomainException;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionValidationException;
import com.wbsedcl.trms.substation.log.domain.mapper.InterruptionDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.AssetRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.OfficeRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class LogInterruptionHelper {

    private final SubstationLogDomainService substationLogDomainService;
    private final SubstationLogRepository substationLogRepository;
    private final UserRepository userRepository;
    private final InterruptionDataMapper interruptionDataMapper;

    public LogInterruptionHelper(SubstationLogDomainService substationLogDomainService,
                                 SubstationLogRepository substationLogRepository,
                                 UserRepository userRepository,
                                 InterruptionDataMapper interruptionDataMapper) {
        this.substationLogDomainService = substationLogDomainService;
        this.substationLogRepository = substationLogRepository;
        this.userRepository = userRepository;
        this.interruptionDataMapper = interruptionDataMapper;
    }

    @Transactional
    public InterruptionLoggedEvent persistInterruption(LogInterruptionCommand command) {
        //1. Validate the restoredBy user
        validateRestoredByUserId(command);
        //2. Get domain object from command
        Interruption interruption = interruptionDataMapper.logInterruptionCommandToInterruption(command);
        //3. Validate and initiate an interruption (domain logic)
        InterruptionLoggedEvent interruptionLoggedEvent = substationLogDomainService.validateAndInitiateInterruption(interruption);
        //4. Save the interruption using repository
        substationLogRepository.save(interruption);
        //5. Log the operation
        log.info("Interruption saved with reference id {}", interruption.getInterruptionRefId());
        //6. return the event with the saved interruption
        return interruptionLoggedEvent;
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
        if ((interruptionType.equals(InterruptionType.TRANSIENT_TRIPPING) || interruptionType.equals(InterruptionType.SOURCE_CHANGEOVER))
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



}
