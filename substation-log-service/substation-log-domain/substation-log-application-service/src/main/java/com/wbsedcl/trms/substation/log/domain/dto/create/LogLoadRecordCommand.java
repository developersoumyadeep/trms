package com.wbsedcl.trms.substation.log.domain.dto.create;

import com.wbsedcl.trms.substation.log.domain.entity.FeederLoadingType;
import com.wbsedcl.trms.substation.log.domain.validation.ValidFeederId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidOfficeId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidUserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LogLoadRecordCommand {
    @ValidFeederId
    private String feederId;
    @ValidOfficeId
    private String substationOfficeId;
    private double load;
    private String remarks;
    @NotNull
    private FeederLoadingType feederLoadingType;
    private String recordedByUserId;
}
