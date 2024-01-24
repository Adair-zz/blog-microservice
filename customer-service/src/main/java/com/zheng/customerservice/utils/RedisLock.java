package com.zheng.customerservice.utils;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/23/2024 - 22:33
 */
public class RedisLock implements ILock {
  
  private String name;
  
  private RedisTemplate redisTemplate;
  
  public RedisLock(String name, RedisTemplate redisTemplate) {
    this.name = name;
    this.redisTemplate = redisTemplate;
  }
  
  private static final String KEY_PREFIX = "lock:";
  
  @Override
  public boolean tryLock(long timeout) {
    Boolean isSuccess = redisTemplate.opsForValue()
        .setIfAbsent(KEY_PREFIX + name, Thread.currentThread().getId() + "", timeout, TimeUnit.MILLISECONDS);
    return Boolean.TRUE.equals(isSuccess);
  }
  
  @Override
  public void unlock() {
    redisTemplate.delete(KEY_PREFIX + name);
  }
}
