package com.wbsedcl.trms.substation.log.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class RestoreInterruptionCommand {
    private UUID interruptionId;
    private LocalDate endDate;
    private LocalTime endTime;
    private String restoredBy;
    private String interruptionCause;
    private String restoredBy33kVSourceId;
    private String restoredByIncomerId;
    private String restoredByPTRId;
    private Boolean _33kVSourceChanged;
    private Boolean feedingPTRChanged;
    private Boolean feedingIncomerChanged;

}
