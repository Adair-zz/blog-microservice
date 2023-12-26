package com.zheng.userservice;

import com.zheng.blogapi.client.UserFeignClient;
import com.zheng.blogapi.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/18/2023 - 15:14
 */
@EnableFeignClients(clients = {UserFeignClient.class}, defaultConfiguration = DefaultFeignConfig.class)
@SpringBootApplication
@MapperScan("com.zheng.userservice.mapper")
@ComponentScan("com.zheng")
public class UserServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }
}
