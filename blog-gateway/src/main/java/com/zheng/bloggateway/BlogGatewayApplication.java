package com.zheng.bloggateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/19/2023 - 20:43
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.zheng")
public class BlogGatewayApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(BlogGatewayApplication.class, args);
  }
  
}
