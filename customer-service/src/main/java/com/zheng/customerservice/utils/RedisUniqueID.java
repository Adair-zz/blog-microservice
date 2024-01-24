package com.zheng.customerservice.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/23/2024 - 21:20
 */
@Component
public class RedisUniqueID {
  
  @Resource
  private RedisTemplate redisTemplate;
  
  private static final long BEGIN_TIMESTAMP = 976752000L;
  
  public long generateId(String keyPrefix) {
    LocalDateTime localDateTime = LocalDateTime.now();
    long second = localDateTime.toEpochSecond(ZoneOffset.UTC);
    long timestamp = second - BEGIN_TIMESTAMP;
    
    String date = localDateTime.format(DateTimeFormatter.ofPattern("MMddyyyy"));
    Long count = redisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);
    
    return timestamp << 32 | count;
  }
  
  public static void main(String[] args) {
    LocalDateTime localDateTime = LocalDateTime.of(2000, 12, 14, 0, 0, 0);
    long second = localDateTime.toEpochSecond(ZoneOffset.UTC);
    System.out.println(second);
  }
}
