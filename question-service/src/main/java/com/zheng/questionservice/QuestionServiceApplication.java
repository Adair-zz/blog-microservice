package com.zheng.questionservice;

import com.zheng.blogapi.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/31/2023 - 15:34
 */
@SpringBootApplication
@MapperScan("com.zheng.questionservice.mapper")
@EnableFeignClients(basePackages = {"com.zheng.blogapi.client"}, defaultConfiguration = {DefaultFeignConfig.class})
public class QuestionServiceApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(QuestionServiceApplication.class, args);
  }
}
