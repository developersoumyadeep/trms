package com.wbsedcl.trms.substation.log.domain.ports.input.service;

import com.wbsedcl.trms.substation.log.domain.dto.create.*;
import com.wbsedcl.trms.substation.log.domain.dto.message.EnergyMeterReadingDTO;
import com.wbsedcl.trms.substation.log.domain.dto.message.InterruptionDTO;

import java.util.List;

public interface SubstationLogApplicationService {

    LogInterruptionResponse logInterruption(LogInterruptionCommand command);

    List<LogInterruptionResponse> logSourceChangeOver(LogSourceChangeOverInterruptionCommand command);

    RestoreInterruptionResponse restoreInterruption(RestoreInterruptionCommand command);

    LogEnergyMeterReadingResponse logEnergyMeterReading(LogEnergyMeterReadingCommand command);

    LogLoadRecordResponse logLoadRecord(LogLoadRecordCommand command);

    List<LogInterruptionResponse> logMainPowerFail(MainPowerFailCommand command);
    List<InterruptionDTO> getAllOpenInterruptionsBySubstationOfficeId(String substationOfficeId);

    List<EnergyMeterReadingDTO> getLatestEnergyMeterReadingsBySubstationOfficeId(String substationOfficeId);
}
