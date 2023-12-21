package com.wbsedcl.trms.substation.log.domain.dto.message;

import com.wbsedcl.trms.substation.log.domain.entity.FeederType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FeederCreatedResponse {
    private String feederId;
    private String feederName;
    private String energyMeterNo;
    private String substationOfficeId;
    private Integer voltageLevel;
    private FeederType feederType;
    private String incomer11kVFeederId;
    private String feedingPTRId;
    private String feeding33kVFeederId;
    private boolean isCharged;
    private boolean isLoaded;
}
