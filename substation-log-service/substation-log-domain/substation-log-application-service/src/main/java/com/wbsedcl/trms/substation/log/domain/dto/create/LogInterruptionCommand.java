package com.wbsedcl.trms.substation.log.domain.dto.create;

import com.wbsedcl.trms.substation.log.domain.entity.FaultNature;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class LogInterruptionCommand {

    @NonNull
    private String faultyAssetId;
    @NotNull
    private String substationOfficeId;
    @NotNull
    private InterruptionType interruptionType;
    @NotNull
    private FaultNature faultNature;
    @NotNull
    private String createdByUserId;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalTime startTime;
    private String restoredByUserId;
    @NotNull
    private InterruptionStatus interruptionStatus;
    private String cause;
    private LocalDate endDate;
    private LocalTime endTime;




}
