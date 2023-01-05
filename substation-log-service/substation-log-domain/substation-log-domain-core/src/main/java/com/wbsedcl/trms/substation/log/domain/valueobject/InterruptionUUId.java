package com.wbsedcl.trms.substation.log.domain.valueobject;

import com.wbsedcl.trms.domain.valueobject.BaseId;

import java.util.UUID;

public class InterruptionUUId extends BaseId<UUID> {
    public InterruptionUUId(UUID value) {
        super(value);
    }
}
