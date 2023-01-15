package com.wbsedcl.trms.substation.log.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class RestoreInterruptionCommand {
    private String interruptionRefId;
    private LocalDate endDate;
    private LocalTime endTime;
    private String restoredBy;

}
