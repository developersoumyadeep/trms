package com.wbsedcl.trms.substation.log.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogEnergyMeterReadingResponse {
    private UUID meterReadingId;
    private String message;
}
