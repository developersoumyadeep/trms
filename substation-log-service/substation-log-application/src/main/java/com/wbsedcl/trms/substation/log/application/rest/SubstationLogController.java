package com.wbsedcl.trms.substation.log.application.rest;

import com.wbsedcl.trms.substation.log.domain.FeederService;
import com.wbsedcl.trms.substation.log.domain.dto.message.EnergyMeterReadingDTO;
import com.wbsedcl.trms.substation.log.domain.dto.message.FeederDTO;
import com.wbsedcl.trms.substation.log.domain.dto.create.*;
import com.wbsedcl.trms.substation.log.domain.dto.message.InterruptionDTO;
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

    //Only the authenticated user with "SITE_USER" role attached to "substationOfficeId" should be able to get the feeders for that substation
    //If the authenticated user is attached to division office then check if the substation office is under that division or not

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
    public ResponseEntity<GroupInterruptionResponse> logSourceChangeOver(@RequestBody LogSourceChangeOverInterruptionCommand command) {
        log.info("sourceChangeOver request received with command {}", command);
        List<LogInterruptionResponse> logInterruptionResponses = substationLogApplicationService.logSourceChangeOver(command);
        GroupInterruptionResponse response = new GroupInterruptionResponse("Success", logInterruptionResponses);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/log-main-power-fail")
    public ResponseEntity<GroupInterruptionResponse> logMainPowerFail(@RequestBody MainPowerFailCommand command) {
        List<LogInterruptionResponse> logInterruptionResponses = substationLogApplicationService.logMainPowerFail(command);
        GroupInterruptionResponse response = new GroupInterruptionResponse("Success", logInterruptionResponses);
        log.info("Returning group interruption response :"+response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/restore-interruption")
    public ResponseEntity<RestoreInterruptionResponse> restoreInterruption(@RequestBody RestoreInterruptionCommand command) {
        log.info("Restoring interruption {} by user {}", command.getInterruptionId(), command.getRestoredBy());
        RestoreInterruptionResponse restoreInterruptionResponse = substationLogApplicationService.restoreInterruption(command);
        log.info("Interruption with ref id {} restored", restoreInterruptionResponse.getInterruptionId());
        return ResponseEntity.ok(restoreInterruptionResponse);
    }

    @PostMapping("/log-energy-meter-reading")
    public ResponseEntity<LogEnergyMeterReadingResponse> logEnergyMeterReading(@RequestBody LogEnergyMeterReadingCommand command) {
        log.info("Logging energy meter reading for feeder id {} at Substation {} recorded by {} ", command.getFeederId(), command.getSubstationOfficeId(), command.getRecordedBy());
        LogEnergyMeterReadingResponse logEnergyMeterReadingResponse = substationLogApplicationService.logEnergyMeterReading(command);
        log.info("Energy meter reading logged with uuid {}",logEnergyMeterReadingResponse.getMeterReadingId());
        return ResponseEntity.ok(logEnergyMeterReadingResponse);
    }

    @PostMapping("/log-load-record")
    public ResponseEntity<LogLoadRecordResponse> logLoadRecord(@RequestBody LogLoadRecordCommand command) {
        log.info("Logging load record for feeder id {} at Substation {} recorded by {} ", command.getFeederId(), command.getSubstationOfficeId(), command.getRecordedBy());
        LogLoadRecordResponse logLoadRecordResponse = substationLogApplicationService.logLoadRecord(command);
        log.info("Load record logged with uuid {}", logLoadRecordResponse.getLoadRecordId());
        return ResponseEntity.ok(logLoadRecordResponse);
    }

    @GetMapping("/open-interruptions/{substationOfficeId}")
    public ResponseEntity<List<InterruptionDTO>> getOpenInterruptions(@PathVariable String substationOfficeId) {
        log.info("Fetching open interruptions of substation :"+substationOfficeId);
        List<InterruptionDTO> dtos = substationLogApplicationService.getAllOpenInterruptionsBySubstationOfficeId(substationOfficeId);
        log.info("Returning open interruptions ..");
        dtos.forEach(dto -> log.info(dto.toString()));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/latest-meter-readings/{substationOfficeId}")
    public ResponseEntity<List<EnergyMeterReadingDTO>> getLatestMeterReadingsAgainstSubstationOfficeId(@PathVariable String substationOfficeId) {
        log.info("Fetching latest meter readings of substation :"+substationOfficeId);
        return ResponseEntity.ok(substationLogApplicationService.getLatestEnergyMeterReadingsBySubstationOfficeId(substationOfficeId));
    }


}

