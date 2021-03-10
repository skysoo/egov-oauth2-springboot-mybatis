package com.study.egovspringbootmybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EgovSpringbootMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(EgovSpringbootMybatisApplication.class, args);
    }

}
