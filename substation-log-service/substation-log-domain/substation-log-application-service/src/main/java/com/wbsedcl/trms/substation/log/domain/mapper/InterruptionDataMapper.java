package com.wbsedcl.trms.substation.log.domain.mapper;

import com.wbsedcl.trms.domain.valueobject.AssetId;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionResponse;
import com.wbsedcl.trms.substation.log.domain.dto.create.RestoreInterruptionResponse;
import com.wbsedcl.trms.substation.log.domain.entity.Interruption;
import com.wbsedcl.trms.substation.log.domain.valueobject.FeederId;
import org.springframework.stereotype.Component;

@Component
public class InterruptionDataMapper {


    public LogInterruptionResponse interruptionToLogInterruptionResponse(Interruption interruption) {
        return LogInterruptionResponse.builder()
                .interruptionId(interruption.getId().getValue())
                .interruptionStatus(interruption.getInterruptionStatus())
                .build();
    }

    public Interruption logInterruptionCommandToInterruption(LogInterruptionCommand command) {
        return Interruption.newBuilder()
                .faultyFeederId(new FeederId(command.getFaultyFeederId()))
                .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
                .interruptionType(command.getInterruptionType())
                .faultNature(command.getFaultNature())
                .createdBy(new UserId(command.getCreatedByUserId()))
                .startDate(command.getStartDate())
                .startTime(command.getStartTime())
                .restoredBy(new UserId(command.getRestoredByUserId()))
                .interruptionStatus(command.getInterruptionStatus())
                .cause(command.getCause())
                .endDate(command.getEndDate())
                .endTime(command.getEndTime())
                .build();
    }

    public RestoreInterruptionResponse interruptionToRestoreInterruptionResponse(Interruption interruption) {
        return RestoreInterruptionResponse.builder()
                .interruptionId(interruption.getId().getValue())
                .interruptionStatus(interruption.getInterruptionStatus())
                .build();
    }
}
