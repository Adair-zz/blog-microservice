package com.zheng.customerservice;

import com.zheng.blogapi.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/23/2024 - 2:06
 */
@MapperScan("com.zheng.customerservice.mapper")
@EnableFeignClients(basePackages = {"com.zheng.blogapi.client"}, defaultConfiguration = {DefaultFeignConfig.class})
@SpringBootApplication
public class CustomerServiceApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(CustomerServiceApplication.class, args);
  }
}
