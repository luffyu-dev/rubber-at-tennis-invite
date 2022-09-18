package com.rubber.at.tennis.invite.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;


@SpringBootTest
@ComponentScan("com.rubber.*")
@MapperScan("com.rubber.**.dao.mapper")
class RubberServerArchetypeWebApplicationTests {


}
