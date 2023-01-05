package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.OfficeId;

public class Office extends BaseEntity<OfficeId> implements AggregateRoot {
    public Office() {
    }

    public Office(OfficeId officeId) {
        setId(officeId);
    }
}
