package com.zheng.customerservice.utils;

import cn.hutool.core.lang.UUID;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
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
  private static final String ID_PREFIX = UUID.randomUUID().toString(true) + "-";
  private static DefaultRedisScript<Long> UNLOCK_SCRIPT;
  
  static {
    UNLOCK_SCRIPT = new DefaultRedisScript<>();
    UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
    UNLOCK_SCRIPT.setResultType(Long.class);
  }
  
  @Override
  public boolean tryLock(long timeout) {
    Boolean isSuccess = redisTemplate.opsForValue()
        .setIfAbsent(KEY_PREFIX + name,
            ID_PREFIX + Thread.currentThread().getId(),
            timeout,
            TimeUnit.MILLISECONDS);
    return Boolean.TRUE.equals(isSuccess);
  }
  
  @Override
  public void unlock() {
    redisTemplate.execute(
        UNLOCK_SCRIPT,
        Collections.singletonList(KEY_PREFIX + name),
        ID_PREFIX + Thread.currentThread().getId());
  }

//  @Override
//  public void unlock() {
//    String threadId = ID_PREFIX + Thread.currentThread().getId();
//    String id = (String) redisTemplate.opsForValue().get(KEY_PREFIX + name);
//    if (threadId.equals(id)) {
//      redisTemplate.delete(KEY_PREFIX + name);
//    }
//  }
}
