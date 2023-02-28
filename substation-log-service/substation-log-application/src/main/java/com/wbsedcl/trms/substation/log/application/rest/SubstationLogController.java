package com.wbsedcl.trms.substation.log.application.rest;

import com.wbsedcl.trms.substation.log.domain.dto.create.*;
import com.wbsedcl.trms.substation.log.domain.ports.input.service.SubstationLogApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/substation-log")
public class SubstationLogController {

    private final SubstationLogApplicationService substationLogApplicationService;

    public SubstationLogController(SubstationLogApplicationService substationLogApplicationService) {
        this.substationLogApplicationService = substationLogApplicationService;
    }

    @PostMapping
    public ResponseEntity<LogInterruptionResponse> logInterruption(@RequestBody LogInterruptionCommand command) {
        log.info("Logging interruption for feeder id {} at Substation {} created by {}", command.getFaultyFeederId(), command.getSubstationOfficeId(), command.getCreatedByUserId());
        LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(command);
        log.info("Interruption logged with id {}", logInterruptionResponse.getInterruptionId());
        return ResponseEntity.ok(logInterruptionResponse);
    }

    @PostMapping
    public ResponseEntity<RestoreInterruptionResponse> restoreInterruption(@RequestBody RestoreInterruptionCommand command) {
        log.info("Restoring interruption {} by user {}", command.getInterruptionId(), command.getRestoredBy());
        RestoreInterruptionResponse restoreInterruptionResponse = substationLogApplicationService.restoreInterruption(command);
        log.info("Interruption with ref id {} restored", restoreInterruptionResponse.getInterruptionId());
        return ResponseEntity.ok(restoreInterruptionResponse);
    }

    @PostMapping
    public ResponseEntity<LogEnergyConsumptionResponse> logEnergyConsumption(@RequestBody LogEnergyConsumptionCommand command) {
        log.info("Logging energy consumption for feeder id {} at Substation {} recorded by {} ", command.getFeederId(), command.getSubstationOfficeId(), command.getRecordedBy());
        LogEnergyConsumptionResponse logEnergyConsumptionResponse = substationLogApplicationService.logEnergyConsumption(command);
        log.info("Energy consumption logged with uuid {}", logEnergyConsumptionResponse.getConsumptionId());
        return ResponseEntity.ok(logEnergyConsumptionResponse);
    }
    
    @PostMapping
    public ResponseEntity<LogLoadRecordResponse> logLoadRecord(@RequestBody LogLoadRecordCommand command) {
        log.info("Logging load record for feeder id {} at Substation {} recorded by {} ", command.getFeederId(), command.getSubstationOfficeId(), command.getRecordedBy());
        LogLoadRecordResponse logLoadRecordResponse = substationLogApplicationService.logLoadRecord(command);
        log.info("Load record logged with uuid {}", logLoadRecordResponse.getLoadRecordId());
        return ResponseEntity.ok(logLoadRecordResponse);
    }
}
