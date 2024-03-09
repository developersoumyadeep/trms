package com.wbsedcl.trms.substation.log.domain.dto.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wbsedcl.trms.substation.log.domain.entity.FaultNature;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionStatus;
import com.wbsedcl.trms.substation.log.domain.entity.InterruptionType;
import com.wbsedcl.trms.substation.log.domain.validation.ValidFeederId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidOfficeId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidUserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class MainPowerFailCommand {
    @ValidFeederId(message = "Given faulty feeder-id is invalid")
    private String faulty33kVSourceFeederId;
    @ValidOfficeId(message = "Given substation office-id is invalid")
    private String substationOfficeId;
    @NotNull
    private InterruptionType _33kVInterruptionType;
    private FaultNature _33kVFaultNature;
    @NotNull
    private InterruptionType outgoingFeederInterruptionType;
    private String createdByUserId;
    private String outgoingFeederRestoredByUserId;
    private String _33kVSourceRestoredByUserId;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate outgoingFeederInterruptionEndDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime outgoingFeederInterruptionEndTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate _33kVInterruptionEndDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime _33kVInterruptionEndTime;
    @NotNull
    private InterruptionStatus _33kVSourceInterruptionStatus;
    private String sourceChangeOverToFeederId;
    private String cause;
    @NotNull
    private List<String> unrestoredFeederIds;
}
