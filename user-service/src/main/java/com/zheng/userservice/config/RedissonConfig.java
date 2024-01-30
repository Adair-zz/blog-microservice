package com.zheng.userservice.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/29/2024 - 18:20
 */
@ConfigurationProperties(value = "spring.data.redis.sentinel")
@Configuration
public class RedissonConfig {
  
  private String masterName;
  
  private String[] sentinelNodes;
  
  @Bean
  public RedissonClient redissonClient() {
    Config config = new Config();
    SentinelServersConfig serversConfig = config.useSentinelServers();
    serversConfig.setMasterName(masterName).addSentinelAddress(sentinelNodes);
    
    RedissonClient redissonClient = Redisson.create(config);
    return redissonClient;
  }
}
