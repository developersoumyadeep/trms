package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.entity.*;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionDomainException;
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
    private final AssetRepository assetRepository;
    private final OfficeRepository officeRepository;

    private final InterruptionDataMapper interruptionDataMapper;

    public LogInterruptionHelper(SubstationLogDomainService substationLogDomainService,
                                 SubstationLogRepository substationLogRepository,
                                 UserRepository userRepository,
                                 AssetRepository assetRepository,
                                 OfficeRepository officeRepository,
                                 InterruptionDataMapper interruptionDataMapper) {
        this.substationLogDomainService = substationLogDomainService;
        this.substationLogRepository = substationLogRepository;
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.officeRepository = officeRepository;
        this.interruptionDataMapper = interruptionDataMapper;
    }

    @Transactional
    public InterruptionLoggedEvent persistInterruption(LogInterruptionCommand command) {
        //1. Validate the createdBy user
        validateCreatedByUserId(command.getCreatedByUserId());
        //2. Validate the restoredBy user
        validateRestoredByUserId(command);
        //3. Validate the faulty asset id
        validateFaultyAssetId(command.getFaultyAssetId());
        //4. Validate the substation office code
        validateSubstationOfficeId(command.getSubstationOfficeId());
        //5. Get domain object from command
        Interruption interruption = interruptionDataMapper.logInterruptionCommandToInterruption(command);
        //6. Validate and initiate an interruption (domain logic)
        InterruptionLoggedEvent interruptionLoggedEvent = substationLogDomainService.validateAndInitiateInterruption(interruption);
        //7. Save the interruption using repository
        substationLogRepository.save(interruption);
        //8. Log the operation
        log.info("Interruption saved with reference id {}", interruption.getInterruptionRefId());
        //9. return the event with the saved interruption
        return interruptionLoggedEvent;
    }

    private void validateSubstationOfficeId(String substationOfficeId) {
        if (substationOfficeId == null || substationOfficeId.equals("")){
            throw new InterruptionDomainException("Interruption must have valid non-null non-empty substation office id");
        }
        Optional<Office> office = officeRepository.findOffice(substationOfficeId);
        if(office.isEmpty()) {
            log.error("Substation office with id {} does not exist", substationOfficeId);
            throw new InterruptionDomainException("Substation office with id "+substationOfficeId+" does not exist");
        }
    }

    private void validateFaultyAssetId(String faultyAssetId) {
        if (faultyAssetId == null || faultyAssetId.trim().equals("")) {
            throw new InterruptionDomainException("Interruption must have valid non-null non-empty asset(feeder/PTR) id");
        }
        Optional<Asset> asset = assetRepository.findAsset(faultyAssetId);
        if(asset.isEmpty()) {
            log.error("Asset with id {} does not exist", faultyAssetId);
            throw new InterruptionDomainException("Asset with id "+faultyAssetId+" does not exist");
        }
    }

    private void validateRestoredByUserId(LogInterruptionCommand command) {
        String createdByUserId = command.getCreatedByUserId();
        String restoredByUserId = command.getRestoredByUserId();
        InterruptionStatus interruptionStatus = command.getInterruptionStatus();
        InterruptionType interruptionType = command.getInterruptionType();
        if (interruptionStatus.equals(InterruptionStatus.NOT_RESTORED) && restoredByUserId != null) {
            throw new InterruptionDomainException("Interruption which is not restored can not have a restored-by user id ");
        }
        if (interruptionStatus.equals(InterruptionStatus.RESTORED) && restoredByUserId == null) {
            throw new InterruptionDomainException("Restored interruption must have a non-null restored-by user id");
        }
        if ((interruptionType.equals(InterruptionType.TRANSIENT_TRIPPING) || interruptionType.equals(InterruptionType.SOURCE_CHANGEOVER))
                    && interruptionStatus.equals(InterruptionStatus.RESTORED)){
            if (!restoredByUserId.equals(createdByUserId)) {
                throw new InterruptionDomainException("Interruption of type 'Transient Tripping' or 'Source Change-over' must have same created-by user id and restored-by user id");
            }
        }
        if (restoredByUserId != null) {
            Optional<User> user = userRepository.findUser(restoredByUserId);
            if(user.isEmpty()) {
                log.error("User with id {} does not exist", restoredByUserId);
                throw new InterruptionDomainException("User with id "+restoredByUserId+" does not exist");
            }
        }

    }

    private void validateCreatedByUserId(String createdByUserId) {
        if (createdByUserId == null) {
            throw new InterruptionDomainException("Interruption must have a non-null created-by user id");
        }
        Optional<User> user = userRepository.findUser(createdByUserId);
        if(user.isEmpty()) {
            log.error("User with id {} does not exist", createdByUserId);
            throw new InterruptionDomainException("User with id "+createdByUserId+" does not exist");
        }
    }


}
