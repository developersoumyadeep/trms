package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogLoadRecordCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogLoadRecordResponse;
import com.wbsedcl.trms.substation.log.domain.event.LoadRecordLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.mapper.LoadRecordDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Slf4j
@Component
public class LogLoadRecordCommandHandler {

    private final LogLoadRecordHelper logLoadRecordHelper;
    private final LoadRecordDataMapper loadRecordDataMapper;

    public LogLoadRecordCommandHandler(LogLoadRecordHelper logLoadRecordHelper,
                                       LoadRecordDataMapper loadRecordDataMapper) {
        this.logLoadRecordHelper = logLoadRecordHelper;
        this.loadRecordDataMapper = loadRecordDataMapper;
    }

    public LogLoadRecordResponse logLoadRecord(@Valid LogLoadRecordCommand command) {
        //1. persist the load record
        LoadRecordLoggedEvent event = logLoadRecordHelper.persistLoadRecord(command);
        //2. log the operation
        log.info("Load record logged with uuid {}", event.getRecord().getId().getValue());
        //3. return the response
        return loadRecordDataMapper.loadRecordToLogLoadRecordResponse(event.getRecord());
    }
}
