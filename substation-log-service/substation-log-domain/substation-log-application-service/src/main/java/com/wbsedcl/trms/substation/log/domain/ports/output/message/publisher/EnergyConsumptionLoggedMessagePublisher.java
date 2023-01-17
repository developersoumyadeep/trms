package com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher;

import com.wbsedcl.trms.substation.log.domain.event.EnergyConsumptionLoggedEvent;

public interface EnergyConsumptionLoggedMessagePublisher {
    void publish(EnergyConsumptionLoggedEvent  event);
}
