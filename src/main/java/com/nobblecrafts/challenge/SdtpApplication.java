package com.nobblecrafts.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.nobblecrafts.challenge.dataaccess")
@EnableJpaRepositories(basePackages = "com.nobblecrafts.challenge.dataaccess")
@ComponentScan(basePackages = "com.nobblecrafts.challenge")
public class SdtpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdtpApplication.class, args);
    }
}