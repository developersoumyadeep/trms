package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.*;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionLoggedEvent;
import com.wbsedcl.trms.substation.log.domain.exception.InterruptionValidationException;
import com.wbsedcl.trms.substation.log.domain.mapper.InterruptionDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher.InterruptionLoggedNotificationMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
        log.info("Interruption is created with id {} ", interruptionLoggedEvent.getInterruption().getId().getValue());
        //3. publish event
//        publisher.publish(interruptionLoggedEvent);
        //4. return response
        return interruptionDataMapper.interruptionToLogInterruptionResponse(interruptionLoggedEvent.getInterruption());
    }


    public List<LogInterruptionResponse> logSourceChangeOver(LogSourceChangeOverInterruptionCommand command) {
        //1. persist the interruptions
        List<InterruptionLoggedEvent> interruptionLoggedEvents = logInterruptionHelper.persistSourceChangeOverInterruption(command);
        //2. log the info
        log.info("Source change over from {} to {} created at {}",
                interruptionLoggedEvents.get(0).getInterruption().getSourceChangeOverFromFeederId(),
                interruptionLoggedEvents.get(0).getInterruption().getSourceChangeOverToFeederId(),
                interruptionLoggedEvents.get(0).getInterruption().getCreationTimeStamp());
        //3. publish the event
//        publisher.publish(interruptionLoggedEvent);
        //4. return the response
        List<LogInterruptionResponse> responses = new ArrayList<>();
        for (InterruptionLoggedEvent event : interruptionLoggedEvents) {
            responses.add(interruptionDataMapper.interruptionToLogInterruptionResponse(event.getInterruption()));
        }
        return responses;
    }

    public List<LogInterruptionResponse> logMainPowerFail(MainPowerFailCommand command) {
        //1. Validate the main power fail command
        //TODO: Replace this validation method with a validation annotation the command itself
        validateMainPowerFailCommand(command);
        //2. persist the interruption
        List<InterruptionLoggedEvent> interruptionLoggedEvents = logInterruptionHelper.persistMainPowerFailInterruption(command);
        //3. log the info
        log.info("Main power fail interruption of {} created at {}", interruptionLoggedEvents.get(0).getInterruption().getFaultyFeeder(), interruptionLoggedEvents.get(0).getInterruption().getCreationTimeStamp());
        //4. publish the event
//        publisher.publish(interruptionLoggedEvent);
        //5. return the response
        List<LogInterruptionResponse> responses = new ArrayList<>();
        for (InterruptionLoggedEvent event : interruptionLoggedEvents) {
            responses.add(interruptionDataMapper.interruptionToLogInterruptionResponse(event.getInterruption()));
        }
        return responses;
//        return interruptionDataMapper.interruptionToLogInterruptionResponse(interruptionLoggedEvent.getInterruption());
    }

    private void validateMainPowerFailCommand(MainPowerFailCommand command){
        log.info("Validating main power fail command for live 33kV source : "+command.getFaulty33kVSourceFeederId());
        if (command.getOutgoingFeederInterruptionEndDate() != null && command.getOutgoingFeederInterruptionEndTime() != null) {
            LocalDateTime startDateTime = LocalDateTime.of(command.getStartDate(),command.getStartTime());
            LocalDateTime endDateTime = LocalDateTime.of(command.getOutgoingFeederInterruptionEndDate(),command.getOutgoingFeederInterruptionEndTime());
            log.info("main power fail start date & time : "+startDateTime);
            log.info("main power fail end date & time : "+endDateTime);
            if (startDateTime.isAfter(endDateTime)) {
                throw new CommandValidationException("Main power fail command start date & time can not be after outgoing feeder interruption end date & time");
            }
        }

    }
}
