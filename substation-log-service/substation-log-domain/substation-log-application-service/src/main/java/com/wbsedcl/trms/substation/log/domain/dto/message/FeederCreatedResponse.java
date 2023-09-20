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
}
