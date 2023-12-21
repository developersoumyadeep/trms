package com.wbsedcl.trms.substation.log.domain.dto.create;

import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.substation.log.domain.dto.message.FeederDTO;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class LogInterruptionResponse {
    private InterruptionStatus interruptionStatus;
    private UUID interruptionId;
    private String message;
    private String interruptedFeederId;
    private Boolean interruptedFeederIsCharged;
    private Boolean interruptedFeederIsLoaded;
}
