package com.zheng.codeservice;

import com.zheng.blogapi.config.DefaultFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 14:57
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.zheng.blogapi.client"}, defaultConfiguration = DefaultFeignConfig.class)
public class CodeServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(CodeServiceApplication.class, args);
  }
}
