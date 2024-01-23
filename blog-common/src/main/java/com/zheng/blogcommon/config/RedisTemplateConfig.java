package com.zheng.blogcommon.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/22/2024 - 18:15
 */
@Configuration
public class RedisTemplateConfig {
  
  @Resource
  private RedisConnectionFactory redisConnectionFactory;
  
  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(RedisSerializer.string());
    return redisTemplate;
  }
}
