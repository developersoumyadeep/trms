package com.wbsedcl.trms.substation.log.domain.dto.create;

import com.wbsedcl.trms.substation.log.domain.validation.ValidEnertgyMeterNo;
import com.wbsedcl.trms.substation.log.domain.validation.ValidFeederId;
import com.wbsedcl.trms.substation.log.domain.validation.ValidOfficeId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LogEnergyMeterReadingCommand {
    @ValidFeederId(message ="Given asset-id for consumption is invalid")
    private String feederId;
    @ValidOfficeId(message = "Given substation office-id is invalid")
    private String substationOfficeId;
    @ValidEnertgyMeterNo(message = "Given energy meter serial number is invalid")
    private String energyMeterNo;
    private Double meterReading;
    private String recordedByUserId;
}
