package com.wbsedcl.trms.substation.log.domain.dto.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyMeter;
import com.wbsedcl.trms.substation.log.domain.entity.EnergyUnit;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EnergyMeterReadingDTO {
    private String meterReadingId;
    private String feederId;
    private String meterSerialNumber;
    private Double meterReading;
    private EnergyUnit energyUnit;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime recordTime;
}
