package com.wbsedcl.hr.management.domain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "hr-management-service")
public class HRManagementServiceConfigData {
    private String employeeCreatedTopicName;
    private String officeCreatedTopicName;
    private String vendorCreatedTopicName;
}
