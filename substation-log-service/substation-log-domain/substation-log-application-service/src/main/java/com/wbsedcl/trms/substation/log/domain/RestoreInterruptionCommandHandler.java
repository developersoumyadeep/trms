package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.dto.create.RestoreInterruptionCommand;
import com.wbsedcl.trms.substation.log.domain.dto.create.RestoreInterruptionResponse;
import com.wbsedcl.trms.substation.log.domain.event.InterruptionRestoredEvent;
import com.wbsedcl.trms.substation.log.domain.mapper.InterruptionDataMapper;
import com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher.InterruptionRestoredNotificationMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RestoreInterruptionCommandHandler {

    private final RestoreInterruptionHelper restoreInterruptionHelper;
    private final InterruptionRestoredNotificationMessagePublisher publisher;

    private final InterruptionDataMapper interruptionDataMapper;

    public RestoreInterruptionCommandHandler(RestoreInterruptionHelper restoreInterruptionHelper,
                                             InterruptionRestoredNotificationMessagePublisher publisher,
                                             InterruptionDataMapper interruptionDataMapper) {
        this.restoreInterruptionHelper = restoreInterruptionHelper;
        this.publisher = publisher;
        this.interruptionDataMapper = interruptionDataMapper;
    }

    public RestoreInterruptionResponse restoreInterruption(RestoreInterruptionCommand command) {

        InterruptionRestoredEvent interruptionRestoredEvent = restoreInterruptionHelper.updateInterruptionStatus(command);
        publisher.publish(interruptionRestoredEvent);
        log.info("Interruption with id {} restored",command.getInterruptionId());
        return interruptionDataMapper.interruptionToRestoreInterruptionResponse(interruptionRestoredEvent.getInterruption());
    }
}
