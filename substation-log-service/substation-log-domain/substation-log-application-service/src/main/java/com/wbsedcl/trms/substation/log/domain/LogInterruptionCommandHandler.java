package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogInterruptionResponse;
import com.wbsedcl.trms.substation.log.domain.dto.create.LogSourceChangeOverInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.mapper.InterruptionDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher.InterruptionLoggedNotificationMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;


@Component
@Slf4j
public class LogInterruptionCommandHandler {

    private final LogInterruptionHelper logInterruptionHelper;

    private final InterruptionDataMapper interruptionDataMapper;

//    private final InterruptionLoggedNotificationMessagePublisher publisher;

//    public LogInterruptionCommandHandler(LogInterruptionHelper logInterruptionHelper,
//                                         InterruptionDataMapper interruptionDataMapper,
//                                         InterruptionLoggedNotificationMessagePublisher publisher) {
//        this.logInterruptionHelper = logInterruptionHelper;
//        this.interruptionDataMapper = interruptionDataMapper;
//        this.publisher = publisher;
//    }

    public LogInterruptionCommandHandler(LogInterruptionHelper logInterruptionHelper,
                                         InterruptionDataMapper interruptionDataMapper
                                         ) {
        this.logInterruptionHelper = logInterruptionHelper;
        this.interruptionDataMapper = interruptionDataMapper;
//        this.publisher = publisher;
    }

    public LogInterruptionResponse logInterruption(@Valid LogInterruptionCommand command) {
        //1. persist the interruption
        InterruptionLoggedEvent interruptionLoggedEvent = logInterruptionHelper.persistInterruption(command);
        //2. log the info
        log.info("Interruption is created with id {} ",interruptionLoggedEvent.getInterruption().getId().getValue());
        //3. publish event
//        publisher.publish(interruptionLoggedEvent);
        //4. return response
        return interruptionDataMapper.interruptionToLogInterruptionResponse(interruptionLoggedEvent.getInterruption());
    }


    public LogInterruptionResponse logSourceChangeOver(LogSourceChangeOverInterruptionCommand command) {
        //1. persist the interruption
        InterruptionLoggedEvent interruptionLoggedEvent = logInterruptionHelper.persistSourceChangeOverInterruption(command);
        //2. log the info
        log.info("Source change over from {} to {} created at {}",
                interruptionLoggedEvent.getInterruption().getSourceChangeOverFromFeederId(),
                interruptionLoggedEvent.getInterruption().getSourceChangeOverToFeederId(),
                interruptionLoggedEvent.getInterruption().getCreationTimeStamp());
        //3. publish the event
//        publisher.publish(interruptionLoggedEvent);
        //4. return the response
        return interruptionDataMapper.interruptionToLogInterruptionResponse(interruptionLoggedEvent.getInterruption());

    }

    public LogInterruptionResponse logMainPowerFail(LogInterruptionCommand command) {
        //1. persist the interruption
        InterruptionLoggedEvent interruptionLoggedEvent = logInterruptionHelper.persistMainPowerFailInterruption(command);
        //2. log the info
        log.info("Main power fail interruption of {} created at {}",interruptionLoggedEvent.getInterruption().getFaultyFeeder(), interruptionLoggedEvent.getInterruption().getCreationTimeStamp());
        //3. publish the event
//        publisher.publish(interruptionLoggedEvent);
        //4. return the response
        return interruptionDataMapper.interruptionToLogInterruptionResponse(interruptionLoggedEvent.getInterruption());
    }
}
