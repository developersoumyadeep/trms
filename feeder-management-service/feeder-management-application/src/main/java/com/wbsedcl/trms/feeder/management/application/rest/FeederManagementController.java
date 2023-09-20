package com.wbsedcl.trms.feeder.management.application.rest;

import com.wbsedcl.trms.feeder.management.domain.dto.create.CreateFeederCommand;
import com.wbsedcl.trms.feeder.management.domain.dto.message.FeederCreatedResponse;
import com.wbsedcl.trms.feeder.management.domain.ports.input.service.FeederManagementApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/feeder-management")
public class FeederManagementController {
    private final FeederManagementApplicationService feederManagementApplicationService;

    public FeederManagementController(FeederManagementApplicationService feederManagementApplicationService) {
        this.feederManagementApplicationService = feederManagementApplicationService;
    }

    @PostMapping("/create")
    public ResponseEntity<FeederCreatedResponse> createNewFeeder(@RequestBody CreateFeederCommand command) {
        log.info("Creating new feeder with id {}", command.getFeederId());
        FeederCreatedResponse feederCreatedResponse = feederManagementApplicationService.createNewFeeder(command);
        log.info("Feeder created with id {}", feederCreatedResponse.getFeederId());
        return ResponseEntity.ok(feederCreatedResponse);
    }
}
