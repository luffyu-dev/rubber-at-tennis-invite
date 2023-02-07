package com.rubber.at.tennis.invite.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

@ComponentScan("com.rubber.*")
@MapperScan("com.rubber.**.dao.mapper")
@SpringBootApplication
public class RubberServerWebApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(RubberServerWebApplication.class, args);
    }

}
