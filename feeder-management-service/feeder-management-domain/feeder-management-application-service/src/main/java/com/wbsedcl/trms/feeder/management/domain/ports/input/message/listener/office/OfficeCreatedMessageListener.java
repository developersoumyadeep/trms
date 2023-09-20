package com.wbsedcl.trms.feeder.management.domain.ports.input.message.listener.office;

import com.wbsedcl.trms.feeder.management.domain.dto.message.OfficeCreatedResponse;

public interface OfficeCreatedMessageListener {

    void newOfficeCreated(OfficeCreatedResponse officeCreatedResponse);
}
