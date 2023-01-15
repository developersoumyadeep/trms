package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogLoadRecordCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogLoadRecordResponse;
import org.springframework.stereotype.Component;

@Component
public class LogLoadRecordCommandHandler {
    public LogLoadRecordResponse logLoadRecord(LogLoadRecordCommand command) {
        return null;
    }
}
