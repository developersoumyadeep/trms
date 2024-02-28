package com.wbsedcl.trms.substation.log.domain.dto.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wbsedcl.trms.substation.log.domain.entity.FaultNature;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InterruptionDTO {
    private String interruptionId;
    private String faultyFeederId;
    private String faultyFeederName;
    private FaultNature faultNature;
    private InterruptionType interruptionType;
    private InterruptionStatus interruptionStatus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

}
