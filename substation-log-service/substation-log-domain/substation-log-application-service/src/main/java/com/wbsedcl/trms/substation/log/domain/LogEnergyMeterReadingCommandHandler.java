package com.wbsedcl.trms.substation.log.domain;


import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyMeterReadingCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogEnergyMeterReadingResponse;
import com.wbsedcl.trms.substation.log.domain.event.EnergyMeterReadingLoggedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogEnergyMeterReadingCommandHandler {
    private final LogEnergyMeterReadingHelper logEnergyMeterReadingHelper;

    public LogEnergyMeterReadingCommandHandler(LogEnergyMeterReadingHelper logEnergyMeterReadingHelper) {
        this.logEnergyMeterReadingHelper = logEnergyMeterReadingHelper;
    }

    public LogEnergyMeterReadingResponse logEnergyMeterReading(LogEnergyMeterReadingCommand command) {
       EnergyMeterReadingLoggedEvent event = logEnergyMeterReadingHelper.persistEnergyMeterReading(command);
       return new LogEnergyMeterReadingResponse(event.getEnergyMeterReading().getId().getValue(), "Success");
       //This is where the code to publish the event for a message queueing system will be written
    }
}
