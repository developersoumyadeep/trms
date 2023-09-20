package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogLoadRecordCommand;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.event.LoadRecordLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.mapper.LoadRecordDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogLoadRecordHelper {
    private final SubstationLogDomainService substationLogDomainService;
    private final SubstationLogRepository substationLogRepository;
    private final LoadRecordDataMapper loadRecordDataMapper;

    public LogLoadRecordHelper(SubstationLogDomainService substationLogDomainService,
                               SubstationLogRepository substationLogRepository,
                               LoadRecordDataMapper loadRecordDataMapper) {
        this.substationLogDomainService = substationLogDomainService;
        this.substationLogRepository = substationLogRepository;
        this.loadRecordDataMapper = loadRecordDataMapper;
    }

    public LoadRecordLoggedEvent persistLoadRecord(LogLoadRecordCommand command) {
        //1. get the domain object from the command
        LoadRecord record = loadRecordDataMapper.logLoadRecordCommandToLoadRecord(command);
        //2. initiate and validate the domain object
        LoadRecordLoggedEvent event = substationLogDomainService.validateAndInitiateLoadRecord(record);
        //3. save the load record using repository
        substationLogRepository.save(event.getRecord());
        //4. log the operation
        log.info("Load record logged with uuid {}", event.getRecord().getId().getValue());
        //5. return the com.wbsedcl.hr.management.domain.event with the saved load record
        return event;
    }
}
