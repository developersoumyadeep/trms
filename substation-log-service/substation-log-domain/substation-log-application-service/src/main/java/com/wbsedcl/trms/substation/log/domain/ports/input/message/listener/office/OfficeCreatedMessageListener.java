package com.wbsedcl.trms.substation.log.domain.ports.input.message.listener.office;

import com.wbsedcl.trms.substation.log.domain.dto.message.OfficeCreatedResponse;

public interface OfficeCreatedMessageListener {
    void newOfficeCreated(OfficeCreatedResponse officeCreatedResponse);
}
