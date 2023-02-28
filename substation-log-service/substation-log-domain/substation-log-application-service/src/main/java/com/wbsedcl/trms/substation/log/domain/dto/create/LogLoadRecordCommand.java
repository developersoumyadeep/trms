package com.wbsedcl.trms.substation.log.domain.dto.create;

import com.wbsedcl.trms.substation.log.domain.validation.ValidFeederId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidOfficeId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidUserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LogLoadRecordCommand {
    @ValidFeederId
    private String feederId;
    @ValidOfficeId
    private String substationOfficeId;
    @ValidUserId
    private String recordedBy;
    private double load;
}
