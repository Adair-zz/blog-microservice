package com.zheng.chatservice;

import com.zheng.blogapi.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/20/2024 - 14:45
 */
@EnableFeignClients(basePackages = {"com.zheng.blogapi.client"}, defaultConfiguration = DefaultFeignConfig.class)
@SpringBootApplication
@ComponentScan("com.zheng")
public class ChatServiceApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(ChatServiceApplication.class, args);
  }
}
