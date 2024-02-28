package com.wbsedcl.trms.substation.log.domain.valueobject;

import com.wbsedcl.trms.domain.valueobject.BaseId;

import java.util.UUID;

public class EnergyMeterReadingId extends BaseId<UUID> {
    public EnergyMeterReadingId(UUID value) {
        super(value);
    }
}
