package com.wbsedcl.trms.substation.log.domain.entity;

import com.wbsedcl.trms.domain.entity.AggregateRoot;
import com.wbsedcl.trms.domain.entity.BaseEntity;
import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;

public class User extends BaseEntity<UserId> implements AggregateRoot {

    private final OfficeId officeId;

    private User(UserBuilder userBuilder) {
        setId(userBuilder.userId);
        this.officeId = userBuilder.officeId;
    }

    public OfficeId getOfficeId() {
        return officeId;
    }

    public static UserBuilder newBuilder() {
        return new UserBuilder();
    }

    public static final class UserBuilder {
        private UserId userId;
        private OfficeId officeId;
       private UserBuilder() {

        }

        public UserBuilder userId(UserId userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder officeId(OfficeId officeId) {
            this.officeId = officeId;
            return this;
        }

        public User build() {
           return new User(this);
        }
    }
}
