package com.wflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wflow.mapper")
public class WflowApplication {
    public static void main(String[] args) {
        SpringApplication.run(WflowApplication.class, args);
    }
} 