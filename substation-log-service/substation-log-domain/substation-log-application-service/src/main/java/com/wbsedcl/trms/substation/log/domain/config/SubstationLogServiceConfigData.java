package com.wbsedcl.trms.substation.log.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "substation-log-service")
public class SubstationLogServiceConfigData {
    private String loggedInterruptionTopicName;
    private String restoredInterruptionTopicName;
    private String loggedEnergyConsumptionTopicName;
    private String loggedLoadRecordTopicName;
}
