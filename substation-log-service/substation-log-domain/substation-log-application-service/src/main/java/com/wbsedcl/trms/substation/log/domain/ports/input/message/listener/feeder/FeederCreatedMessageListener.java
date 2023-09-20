package com.wbsedcl.trms.substation.log.domain.ports.input.message.listener.feeder;

import com.wbsedcl.trms.substation.log.domain.dto.message.FeederCreatedResponse;

public interface FeederCreatedMessageListener {
    void saveNewlyCreatedFeeder(FeederCreatedResponse feederCreatedResponse);
}
