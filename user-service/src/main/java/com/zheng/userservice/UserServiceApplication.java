package com.zheng.userservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/18/2023 - 15:14
 */
@SpringBootApplication
@MapperScan("com.zheng.userservice.mapper")
@ComponentScan("com.zheng")
public class UserServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }
}
