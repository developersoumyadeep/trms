package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.LogLoadRecordCommand;
import com.wbsedcl.trms.substation.log.domain.dto.LogLoadRecordResponse;
import org.springframework.stereotype.Component;

@Component
public class LogLoadRecordCommandHandler {
    public LogLoadRecordResponse logLoadRecord(LogLoadRecordCommand command) {
        return null;
    }
}
