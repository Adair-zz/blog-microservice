package com.zheng.bloggateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/20/2023 - 23:55
 */
public class GlobalAuthFilter implements GlobalFilter, Ordered {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    
    
    return null;
  }
  
  @Override
  public int getOrder() {
    return 0;
  }
}
