package com.wbsedcl.trms.substation.log.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public SubstationLogDomainService orderDomainService() {
        return new SubstationLogDomainServiceImpl();
    }
}
