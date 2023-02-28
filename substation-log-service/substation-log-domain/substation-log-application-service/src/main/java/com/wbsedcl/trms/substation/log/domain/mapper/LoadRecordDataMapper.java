package com.wbsedcl.trms.substation.log.domain.mapper;

import com.wbsedcl.trms.domain.valueobject.OfficeId;
import com.wbsedcl.trms.domain.valueobject.UserId;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogLoadRecordCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogLoadRecordResponse;
import com.wbsedcl.trms.substation.log.domain.entity.LoadRecord;
import com.wbsedcl.trms.substation.log.domain.valueobject.FeederId;
import org.springframework.stereotype.Component;

@Component
public class LoadRecordDataMapper {

    public LoadRecord logLoadRecordCommandToLoadRecord(LogLoadRecordCommand command){
       return LoadRecord.newBuilder()
               .feederId(new FeederId(command.getFeederId()))
               .load(command.getLoad())
               .recordedBy(new UserId(command.getRecordedBy()))
               .substationOfficeId(new OfficeId(command.getSubstationOfficeId()))
               .build();
    }
    public LogLoadRecordResponse loadRecordToLogLoadRecordResponse(LoadRecord record) {
        String message = "Load record for feeder id " + record.getFeederId() + " has been recorded at "+record.getTime() +" hrs on "+record.getDate();
        return LogLoadRecordResponse.builder()
                .loadRecordId(record.getId().getValue())
                .message(message)
                .build();
    }
}
