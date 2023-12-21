package com.wbsedcl.trms.substation.log.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.wbsedcl.trms.substation.log.dataaccess")
@EntityScan(basePackages = "com.wbsedcl.trms.substation.log.dataaccess")
@SpringBootApplication(scanBasePackages = {"com.wbsedcl.trms.substation.log", "com.wbsedcl.trms.kafka"})
//@SpringBootApplication(scanBasePackages = {"com.wbsedcl.trms.substation.log"})
public class SubstationLogServiceApplication {
    public static void main(String[] args) {
         SpringApplication.run(SubstationLogServiceApplication.class, args);
    }
}
