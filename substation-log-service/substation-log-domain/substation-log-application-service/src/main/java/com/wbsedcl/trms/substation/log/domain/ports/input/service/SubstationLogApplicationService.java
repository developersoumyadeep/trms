package com.wbsedcl.trms.substation.log.domain.ports.input.service;

import com.wbsedcl.trms.substation.log.domain.dto.create.*;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;

import java.util.List;

public interface SubstationLogApplicationService {

    LogInterruptionResponse logInterruption(LogInterruptionCommand command);

    LogInterruptionResponse logSourceChangeOver(LogSourceChangeOverInterruptionCommand command);

    RestoreInterruptionResponse restoreInterruption(RestoreInterruptionCommand command);

    LogEnergyConsumptionResponse logEnergyConsumption(LogEnergyConsumptionCommand command);

    LogLoadRecordResponse logLoadRecord(LogLoadRecordCommand command);

    LogInterruptionResponse logMainPowerFail(LogInterruptionCommand command);
}
