package com.penekhun.sms_demo_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SmsDemoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsDemoSpringApplication.class, args);
    }

}
