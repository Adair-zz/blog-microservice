package com.zheng.customerservice.utils;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/23/2024 - 22:33
 */
@Slf4j
public class RedisLock implements ILock {
  
  private String name;
  
  private StringRedisTemplate stringRedisTemplate;
  
  private long timeout;
  
  public RedisLock(String name, StringRedisTemplate stringRedisTemplate, long timeout) {
    this.name = name;
    this.stringRedisTemplate = stringRedisTemplate;
    this.timeout = timeout;
  }
  
  private static final String KEY_PREFIX = "lock:";
  private static final String ID_PREFIX = UUID.randomUUID().toString(true) + "-";
  private static DefaultRedisScript<Long> UNLOCK_SCRIPT;
  private static DefaultRedisScript<Long> REENTRANT_UNLOCK_SCRIPT;
  private static DefaultRedisScript<Long> REENTRANT_LOCK_SCRIPT;
  
  static {
    UNLOCK_SCRIPT = new DefaultRedisScript<>();
    UNLOCK_SCRIPT.setLocation(new ClassPathResource("lua/unlock.lua"));
    UNLOCK_SCRIPT.setResultType(Long.class);
  }
  
  static {
    REENTRANT_UNLOCK_SCRIPT = new DefaultRedisScript<>();
    REENTRANT_UNLOCK_SCRIPT.setLocation(new ClassPathResource("lua/reentrant_unlock.lua"));
    REENTRANT_UNLOCK_SCRIPT.setResultType(Long.class);
  }
  
  static {
    REENTRANT_LOCK_SCRIPT = new DefaultRedisScript<>();
    REENTRANT_LOCK_SCRIPT.setLocation(new ClassPathResource("lua/reentrant_lock.lua"));
    REENTRANT_LOCK_SCRIPT.setResultType(Long.class);
  }
  
  @Override
  public boolean tryLock() {
    String key = KEY_PREFIX + name;
    String threadId = ID_PREFIX + Thread.currentThread().getId();
    Object result = stringRedisTemplate.execute(
        REENTRANT_LOCK_SCRIPT,
        Collections.singletonList(key),
        threadId, String.valueOf(timeout)
    );
    return result != null && (Long) result == 1L;
    // 可重入锁获取
//    Long result = redisTemplate.opsForHash().increment(key, threadId, 1);
//    redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
//    return result == 1 ? true : false;
    // 普通锁获取
//    Boolean isSuccess = redisTemplate.opsForValue()
//        .setIfAbsent(key,
//            threadId,
//            timeout,
//            TimeUnit.SECONDS);
//    return Boolean.TRUE.equals(isSuccess);
  }
  
  @Override
  public void unlock() {
    String key = KEY_PREFIX + name;
    String threadId = ID_PREFIX + Thread.currentThread().getId();
    // 可重入锁释放
    stringRedisTemplate.execute(
        REENTRANT_UNLOCK_SCRIPT,
        Collections.singletonList(key),
        threadId, String.valueOf(timeout)
    );
    // 普通锁释放
//    redisTemplate.execute(
//        UNLOCK_SCRIPT,
//        Collections.singletonList(key),
//        threadId);
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
