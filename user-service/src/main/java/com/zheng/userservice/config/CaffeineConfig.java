package com.zheng.userservice.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zheng.blogcommon.model.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/29/2024 - 18:32
 */
@Configuration
public class CaffeineConfig {
  
  @Bean
  public Cache<Long, User> userCache() {
    return Caffeine.newBuilder()
        .initialCapacity(100)
        .maximumSize(1000)
        .build();
  }
}
