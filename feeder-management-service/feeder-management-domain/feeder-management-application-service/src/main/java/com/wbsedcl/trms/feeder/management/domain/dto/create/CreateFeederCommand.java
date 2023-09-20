package com.wbsedcl.trms.feeder.management.domain.dto.create;

import com.wbsedcl.trms.feeder.management.domain.entity.FeederType;
import com.wbsedcl.trms.feeder.management.domain.validation.ValidFeederId;
import com.wbsedcl.trms.feeder.management.domain.validation.ValidFeederText;
import com.wbsedcl.trms.feeder.management.domain.validation.ValidOfficeId;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreateFeederCommand {
    @ValidFeederId(message = "Invalid feeder id entered")
    private String feederId;
    @ValidFeederText(message = "Entered feeder text is invalid")
    private String feederText;
    private Integer voltageLevel;
    @ValidOfficeId(message = "Entered office id is invalid")
    private String sourceSubstationOfficeId;
    @ValidOfficeId(message = "Entered office id is invalid")
    private String terminalSubstationOfficeId;
    private String energyMeterNoAtSource;
    private String energyMeterNoAtTerminal;
    private Double gisLength;
    private Double ctRatioAtSource;
    private Double ctRatioAtTerminal;
    private Double multiplyingFactor;
    private FeederType feederType;
    private String ptrId;
    private Double incomerCTRatio;
    private String switchgearId;
    private Boolean isDedicatedBulkFeeder;
}
