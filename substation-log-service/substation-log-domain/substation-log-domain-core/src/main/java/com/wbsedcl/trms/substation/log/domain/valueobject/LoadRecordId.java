package com.wbsedcl.trms.substation.log.domain.valueobject;

import com.wbsedcl.trms.domain.valueobject.BaseId;

import java.util.UUID;

public class LoadRecordId extends BaseId<UUID> {

    public LoadRecordId(UUID value) {
        super(value);
    }
}
