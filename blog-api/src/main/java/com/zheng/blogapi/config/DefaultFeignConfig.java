package com.zheng.blogapi.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/18/2023 - 23:34
 */
public class DefaultFeignConfig {
  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
