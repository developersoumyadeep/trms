package com.wbsedcl.trms.substation.log.domain.valueobject;

import com.wbsedcl.trms.domain.valueobject.BaseId;

import java.util.UUID;

public class EnergyConsumptionId extends BaseId<UUID> {
    public EnergyConsumptionId(UUID value) {
        super(value);
    }
}
