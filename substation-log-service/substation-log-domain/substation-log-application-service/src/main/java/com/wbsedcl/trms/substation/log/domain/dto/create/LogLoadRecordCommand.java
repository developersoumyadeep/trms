package com.wbsedcl.trms.substation.log.domain.dto.create;

import com.wbsedcl.trms.substation.log.domain.validation.ValidAssetId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidOfficeId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidUserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class LogLoadRecordCommand {
    @ValidAssetId
    private String assetId;
    @ValidOfficeId
    private String substationOfficeId;
    @ValidUserId
    private String recordedBy;
    private double load;
}
