package com.wbsedcl.trms.substation.log.domain.mapper;

import com.wbsedcl.trms.substation.log.domain.dto.LogInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.LogInterruptionResponse;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;

public class InterruptionDataMapper {


    public LogInterruptionResponse interruptionToLogInterruptionResponse(Interruption interruption) {
        return LogInterruptionResponse.builder()
                .interruptionRefId(interruption.getInterruptionRefId())
                .interruptionStatus(interruption.getInterruptionStatus())
                .build();
    }

    public Interruption logInterruptionCommandToInterruption(LogInterruptionCommand command) {
        return null;
    }
}
