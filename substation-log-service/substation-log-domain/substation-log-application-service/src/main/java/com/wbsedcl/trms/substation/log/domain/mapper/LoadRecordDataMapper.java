package com.wbsedcl.trms.substation.log.domain.mapper;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogLoadRecordCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogLoadRecordResponse;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import org.springframework.stereotype.Component;

@Component
public class LoadRecordDataMapper {

    public LoadRecord logLoadRecordCommandToLoadRecord(LogLoadRecordCommand command){
        return null;
    }
    public LogLoadRecordResponse loadRecordToLogLoadRecordResponse(LoadRecord record) {
        return null;
    }
}
