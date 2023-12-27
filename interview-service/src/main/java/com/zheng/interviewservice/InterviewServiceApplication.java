package com.zheng.interviewservice;

import com.zheng.blogapi.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/24/2023 - 18:38
 */
@SpringBootApplication
@MapperScan("com.zheng.interviewservice.mapper")
@EnableFeignClients(basePackages = {"com.zheng.blogapi.client"}, defaultConfiguration = {DefaultFeignConfig.class})
public class InterviewServiceApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(InterviewServiceApplication.class, args);
  }
  
}
