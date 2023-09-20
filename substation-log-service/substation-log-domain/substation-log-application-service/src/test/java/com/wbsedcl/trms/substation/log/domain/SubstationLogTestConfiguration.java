package com.wbsedcl.trms.substation.log.domain;

import com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher.InterruptionLoggedNotificationMessagePublisher;
import com.wbsedcl.trms.substation.log.domain.ports.output.message.publisher.InterruptionRestoredNotificationMessagePublisher;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.FeederRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.OfficeRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.SubstationLogRepository;
import com.wbsedcl.trms.substation.log.domain.ports.output.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.wbsedcl.trms")
public class SubstationLogTestConfiguration {

    @Bean
    public InterruptionLoggedNotificationMessagePublisher interruptionLoggedNotificationMessagePublisher() {
        return Mockito.mock(InterruptionLoggedNotificationMessagePublisher.class);
    }

    @Bean
    public InterruptionRestoredNotificationMessagePublisher interruptionRestoredNotificationMessagePublisher() {
        return Mockito.mock(InterruptionRestoredNotificationMessagePublisher.class);
    }

    @Bean
    public SubstationLogRepository substationLogRepository() {
        return Mockito.mock(SubstationLogRepository.class);
    }

    @Bean
    public FeederRepository assetRepository() {
        return Mockito.mock(FeederRepository.class);
    }

    @Bean
    public OfficeRepository officeRepository() {
        return Mockito.mock(OfficeRepository.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    public SubstationLogDomainService substationLogDomainService() {
        return new SubstationLogDomainServiceImpl();
    }
}
