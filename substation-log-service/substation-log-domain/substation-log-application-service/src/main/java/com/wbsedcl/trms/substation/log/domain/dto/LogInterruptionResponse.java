package com.wbsedcl.trms.substation.log.domain.dto;

import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LogInterruptionResponse {
    private InterruptionStatus interruptionStatus;
    private String interruptionRefId;
}
