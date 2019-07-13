package com.cuihui.rabbit.springbootproducer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cuihui.rabbit.springbootproducer.dao")  //扫描mybatis下所有的mapper,可替代dao层的@Mapper注解
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
