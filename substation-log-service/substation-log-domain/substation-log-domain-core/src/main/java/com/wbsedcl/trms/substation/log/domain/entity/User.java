package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.UserId;

public class User extends BaseEntity<UserId> implements AggregateRoot {
    public User() {

    }

    public User (UserId userId) {
        super.setId(userId);
    }
}
