package com.wbsedcl.trms.substation.log.domain.ports.input.message.listener.user;

import com.wbsedcl.trms.substation.log.domain.dto.message.UserCreatedResponse;

public interface UserCreatedMessageListener {
    void newUserCreated(UserCreatedResponse userCreatedResponse);
}
