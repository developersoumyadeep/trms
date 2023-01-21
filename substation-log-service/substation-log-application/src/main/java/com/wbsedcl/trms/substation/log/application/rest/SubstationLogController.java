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
        log.info("Logging interruption for assetId {} at Substation {} created by {}", command.getFaultyAssetId(), command.getSubstationOfficeId(), command.getCreatedByUserId());
        LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(command);
        log.info("Interruption logged with reference id {}", logInterruptionResponse.getInterruptionRefId());
        return ResponseEntity.ok(logInterruptionResponse);
    }

    @PostMapping
    public ResponseEntity<RestoreInterruptionResponse> restoreInterruption(@RequestBody RestoreInterruptionCommand command) {
        log.info("Restoring interruption {} by user {}", command.getInterruptionRefId(), command.getRestoredBy());
        RestoreInterruptionResponse restoreInterruptionResponse = substationLogApplicationService.restoreInterruption(command);
        log.info("Interruption with ref id {} restored", restoreInterruptionResponse.getInterruptionRefId());
        return ResponseEntity.ok(restoreInterruptionResponse);
    }

    @PostMapping
    public ResponseEntity<LogEnergyConsumptionResponse> logEnergyConsumption(@RequestBody LogEnergyConsumptionCommand command) {
        log.info("Logging energy consumption for assetId {} at Substation {} recorded by {} ", command.getAssetId(), command.getSubstationOfficeId(), command.getRecordedBy());
        LogEnergyConsumptionResponse logEnergyConsumptionResponse = substationLogApplicationService.logEnergyConsumption(command);
        log.info("Energy consumption logged with uuid {}", logEnergyConsumptionResponse.getConsumptionId());
        return ResponseEntity.ok(logEnergyConsumptionResponse);
    }
    
    @PostMapping
    public ResponseEntity<LogLoadRecordResponse> logLoadRecord(@RequestBody LogLoadRecordCommand command) {
        log.info("Logging load record for assetId {} at Substation {} recorded by {} ", command.getAssetId(), command.getSubstationOfficeId(), command.getRecordedBy());
        LogLoadRecordResponse logLoadRecordResponse = substationLogApplicationService.logLoadRecord(command);
        log.info("Load record logged with uuid {}", logLoadRecordResponse.getLoadRecordId());
        return ResponseEntity.ok(logLoadRecordResponse);
    }
}
