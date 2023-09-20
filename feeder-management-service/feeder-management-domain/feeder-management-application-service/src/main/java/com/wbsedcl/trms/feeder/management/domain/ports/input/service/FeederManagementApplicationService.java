package com.wbsedcl.trms.feeder.management.domain.ports.input.service;

import com.wbsedcl.trms.feeder.management.domain.dto.create.CreateFeederCommand;
import com.wbsedcl.trms.feeder.management.domain.dto.message.FeederCreatedResponse;

public interface FeederManagementApplicationService {

    FeederCreatedResponse createNewFeeder(CreateFeederCommand command);

}
