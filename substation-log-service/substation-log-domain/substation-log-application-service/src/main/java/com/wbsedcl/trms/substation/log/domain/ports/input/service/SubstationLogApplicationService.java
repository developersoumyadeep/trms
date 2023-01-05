package com.wbsedcl.trms.substation.log.domain.ports.input.service;

import com.wbsedcl.trms.substation.log.domain.dto.*;

public interface SubstationLogApplicationService {

    LogInterruptionResponse logInterruption(LogInterruptionCommand command);

    RestoreInterruptionResponse restoreInterruption(RestoreInterruptionCommand command);

    LogEnergyConsumptionResponse logEnergyConsumption(LogEnergyConsumptionCommand command);

    LogLoadRecordResponse logLoadRecord(LogLoadRecordCommand command);
}
