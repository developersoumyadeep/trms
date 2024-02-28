package com.wbsedcl.trms.substation.log.domain.dto.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wbsedcl.trms.domain.valueobject.FeederId;
import com.wbsedcl.trms.substation.log.domain.dto.message.FeederDTO;
import com.wbsedcl.trms.substation.log.domain.entity.FaultNature;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class LogInterruptionResponse {
    private InterruptionStatus interruptionStatus;
    private UUID interruptionId;
    private String message;
    private String interruptedFeederId;
    private Boolean interruptedFeederIsCharged;
    private Boolean interruptedFeederIsLoaded;
    private FaultNature interruptionFaultNature;
    private InterruptionType interruptionType;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate interruptionStartDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime interruptionStartTime;
}
