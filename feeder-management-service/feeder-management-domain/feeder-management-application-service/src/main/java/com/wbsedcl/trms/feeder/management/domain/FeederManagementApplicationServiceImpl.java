package com.wbsedcl.trms.feeder.management.domain;

import com.wbsedcl.trms.feeder.management.domain.dto.create.CreateFeederCommand;
import com.wbsedcl.trms.feeder.management.domain.dto.message.FeederCreatedResponse;
import com.wbsedcl.trms.feeder.management.domain.ports.input.service.FeederManagementApplicationService;
import org.springframework.stereotype.Service;

@Service
public class FeederManagementApplicationServiceImpl implements FeederManagementApplicationService {

    private final CreateFeederCommandHandler createFeederCommandHandler;

    public FeederManagementApplicationServiceImpl(CreateFeederCommandHandler createFeederCommandHandler) {
        this.createFeederCommandHandler = createFeederCommandHandler;
    }

    @Override
    public FeederCreatedResponse createNewFeeder(CreateFeederCommand command) {
        return createFeederCommandHandler.createFeeder(command);
    }
}
