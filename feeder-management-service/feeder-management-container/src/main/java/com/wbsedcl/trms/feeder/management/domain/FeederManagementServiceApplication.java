package com.wbsedcl.trms.feeder.management.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.wbsedcl.trms.feeder.management.dataaccess")
@EntityScan(basePackages = "com.wbsedcl.trms.feeder.management.dataaccess")
@SpringBootApplication(scanBasePackages = {"com.wbsedcl.trms.feeder.management", "com.wbsedcl.trms.kafka"})
public class FeederManagementServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeederManagementServiceApplication.class, args);
    }
}
