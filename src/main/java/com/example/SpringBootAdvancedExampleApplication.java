package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootAdvancedExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdvancedExampleApplication.class, args);
    }
}
