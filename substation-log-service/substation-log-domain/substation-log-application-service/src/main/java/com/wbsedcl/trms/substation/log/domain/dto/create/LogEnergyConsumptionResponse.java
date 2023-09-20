package com.wbsedcl.trms.substation.log.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class LogEnergyConsumptionResponse {
    private String message;
    private UUID consumptionId;
}
