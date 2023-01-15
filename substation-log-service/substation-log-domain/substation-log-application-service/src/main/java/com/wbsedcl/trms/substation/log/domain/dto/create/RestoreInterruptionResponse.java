package com.wbsedcl.trms.substation.log.domain.dto.create;


import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestoreInterruptionResponse {
    private InterruptionStatus interruptionStatus;
    private String interruptionRefId;
}
