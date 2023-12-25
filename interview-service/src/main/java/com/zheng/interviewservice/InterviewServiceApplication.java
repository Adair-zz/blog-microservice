package com.zheng.interviewservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/24/2023 - 18:38
 */
@SpringBootApplication
@MapperScan("com.zheng.interviewservice.mapper")
public class InterviewServiceApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(InterviewServiceApplication.class, args);
  }
  
}
