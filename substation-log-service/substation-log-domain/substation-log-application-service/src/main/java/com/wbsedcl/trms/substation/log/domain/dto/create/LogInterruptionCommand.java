package com.wbsedcl.trms.substation.log.domain.dto.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wbsedcl.trms.substation.log.domain.entity.FaultNature;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionType;
import com.wbsedcl.trms.substation.log.domain.validation.ValidFeederId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidOfficeId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidUserId;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class LogInterruptionCommand {

    @ValidFeederId(message = "Given faulty asset-id is invalid")
    private String faultyFeederId;
    @ValidOfficeId(message = "Given substation office-id is invalid")
    private String substationOfficeId;
    @NotNull
    private InterruptionType interruptionType;
    @NotNull
    private FaultNature faultNature;
    @ValidUserId(message="Created-by user-id is invalid")
    private String createdByUserId;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    private String restoredByUserId;
    @NotNull
    private InterruptionStatus interruptionStatus;
    private String cause;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private String sourceChangeOverToFeederId;

}
