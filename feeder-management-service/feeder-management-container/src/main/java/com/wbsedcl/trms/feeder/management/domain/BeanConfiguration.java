package com.wbsedcl.trms.feeder.management.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public FeederDomainService feederManagementDomainService() {
        return new FeederDomainServiceImpl();
    }
}
