package com.wbsedcl.trms.substation.log.domain.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wbsedcl.trms.substation.log.domain.entity.FeederType;
import lombok.*;

import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeederDTO {
    private String feederId;
    private String feederName;
    private String substationOfficeId;
    private String energyMeterNo;
    private Integer voltageLevel;
    private FeederType feederType;
    private FeederDTO incoming11kVFeeder;
    private FeederDTO feedingPTR;
    private FeederDTO feeding33kVFeeder;
    private Boolean isCharged;
    private Boolean isLoaded;
}
