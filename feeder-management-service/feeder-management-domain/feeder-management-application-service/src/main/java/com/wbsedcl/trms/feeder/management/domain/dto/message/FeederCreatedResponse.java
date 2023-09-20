package com.wbsedcl.trms.feeder.management.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Builder
@Getter
public class FeederCreatedResponse {
    private String feederId;
    private String feederText;
    private String sourceSubstationId;
    private Integer voltageLevel;
}

