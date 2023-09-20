package com.wbsedcl.trms.feeder.management.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "feeder-service")
public class FeederManagementServiceConfigData {
    private String feederCreatedTopicName;
    private String feederUpdatedTopicName;
    private String feederDeletedTopicName;
}
