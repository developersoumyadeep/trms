package com.wbsedcl.trms.substation.log.application.rest;

import com.wbsedcl.trms.substation.log.domain.FeederService;
import com.wbsedcl.trms.substation.log.domain.dto.message.FeederDTO;
import com.wbsedcl.trms.substation.log.domain.dto.create.*;
import com.wbsedcl.trms.substation.log.domain.entity.Feeder;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import com.wbsedcl.trms.substation.log.domain.mapper.FeederServiceDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.input.service.SubstationLogApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/substation-log")
public class SubstationLogController {
    private final SubstationLogApplicationService substationLogApplicationService;
    private final FeederService feederService;
    private final FeederServiceDataMapper mapper;

    public SubstationLogController(SubstationLogApplicationService substationLogApplicationService, FeederService feederService, FeederServiceDataMapper mapper) {
        this.substationLogApplicationService = substationLogApplicationService;
        this.feederService = feederService;
        this.mapper = mapper;
    }

    @GetMapping("/feeders/{substationOfficeId}")
    public ResponseEntity<List<FeederDTO>> getFeedersBySubstationOfficeId(@PathVariable String substationOfficeId) {
        List<FeederDTO> responseFeeders = new ArrayList<>();
        List<Feeder> feeders = feederService.getFeedersBySubstationOfficeId(substationOfficeId);
        for (Feeder feeder : feeders) {
            responseFeeders.add(mapper.feederToFeederDto(feeder));
        }
        return ResponseEntity.ok(responseFeeders);
    }

    @PostMapping("/log-interruption")
    public ResponseEntity<LogInterruptionResponse> logInterruption(@RequestBody LogInterruptionCommand command) {
        log.info("Logging interruption for feeder id {} at Substation {} created by {}", command.getFaultyFeederId(), command.getSubstationOfficeId(), command.getCreatedByUserId());
        if(command.getInterruptionStatus().equals(InterruptionStatus.RESTORED)){
            log.info("The interruption was restored by {}", command.getRestoredByUserId());
        }
        LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logInterruption(command);
        log.info("Interruption logged with id {}", logInterruptionResponse.getInterruptionId());
        return ResponseEntity.ok(logInterruptionResponse);
    }

    @PostMapping("/log-source-change-over")
    public ResponseEntity<LogInterruptionResponse> logSourceChangeOver(@RequestBody LogSourceChangeOverInterruptionCommand command) {
        log.info("sourceChangeOver request received with command {}", command);
        LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logSourceChangeOver(command);
        return ResponseEntity.ok(logInterruptionResponse);
    }

    @PostMapping("/log-main-power-fail")
    public ResponseEntity<LogInterruptionResponse> logMainPowerFail(@RequestBody LogInterruptionCommand command) {
        LogInterruptionResponse logInterruptionResponse = substationLogApplicationService.logMainPowerFail(command);
        return ResponseEntity.ok(logInterruptionResponse);
    }

    @PostMapping("/restore-interruption")
    public ResponseEntity<RestoreInterruptionResponse> restoreInterruption(@RequestBody RestoreInterruptionCommand command) {
        log.info("Restoring interruption {} by user {}", command.getInterruptionId(), command.getRestoredBy());
        RestoreInterruptionResponse restoreInterruptionResponse = substationLogApplicationService.restoreInterruption(command);
        log.info("Interruption with ref id {} restored", restoreInterruptionResponse.getInterruptionId());
        return ResponseEntity.ok(restoreInterruptionResponse);
    }

    @PostMapping("/log-energy-consumption")
    public ResponseEntity<LogEnergyConsumptionResponse> logEnergyConsumption(@RequestBody LogEnergyConsumptionCommand command) {
        log.info("Logging energy consumption for feeder id {} at Substation {} recorded by {} ", command.getFeederId(), command.getSubstationOfficeId(), command.getRecordedBy());
        LogEnergyConsumptionResponse logEnergyConsumptionResponse = substationLogApplicationService.logEnergyConsumption(command);
        log.info("Energy consumption logged with uuid {}", logEnergyConsumptionResponse.getConsumptionId());
        return ResponseEntity.ok(logEnergyConsumptionResponse);
    }

    @PostMapping("/log-load-record")
    public ResponseEntity<LogLoadRecordResponse> logLoadRecord(@RequestBody LogLoadRecordCommand command) {
        log.info("Logging load record for feeder id {} at Substation {} recorded by {} ", command.getFeederId(), command.getSubstationOfficeId(), command.getRecordedBy());
        LogLoadRecordResponse logLoadRecordResponse = substationLogApplicationService.logLoadRecord(command);
        log.info("Load record logged with uuid {}", logLoadRecordResponse.getLoadRecordId());
        return ResponseEntity.ok(logLoadRecordResponse);
    }
}

