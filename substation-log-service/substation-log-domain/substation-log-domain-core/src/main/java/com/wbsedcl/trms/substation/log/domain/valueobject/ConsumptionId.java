package com.wbsedcl.trms.substation.log.domain.valueobject;

import com.wbsedcl.trms.domain.valueobject.BaseId;

import java.util.UUID;

public class ConsumptionId extends BaseId<UUID> {
    public ConsumptionId(UUID value) {
        super(value);
    }
}
