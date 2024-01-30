package com.zheng.questionservice.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.zheng.blogcommon.model.entity.Question;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/29/2024 - 21:50
 */
@Configuration
public class CoffeineConfig {
  
  @Bean
  public Cache<Long, Question> questionCache() {
    return Caffeine.newBuilder()
        .initialCapacity(100)
        .maximumSize(1000)
        .build();
  }
}
