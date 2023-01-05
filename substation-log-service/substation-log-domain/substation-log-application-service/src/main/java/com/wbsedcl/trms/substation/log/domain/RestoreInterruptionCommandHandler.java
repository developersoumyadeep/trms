package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.RestoreInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.RestoreInterruptionResponse;
import org.springframework.stereotype.Component;

@Component
public class RestoreInterruptionCommandHandler {
    public RestoreInterruptionResponse restoreInterruption(RestoreInterruptionCommand command) {
        return null;
    }
}
