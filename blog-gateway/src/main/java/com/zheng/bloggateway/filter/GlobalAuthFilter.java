package com.zheng.bloggateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/20/2023 - 23:55
 */
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {
  
  private AntPathMatcher antPathMatcher = new AntPathMatcher();
  
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest serverHttpRequest = exchange.getRequest();
    String path = serverHttpRequest.getURI().getPath();
    if (antPathMatcher.match("/**/inner/**", path)) {
      ServerHttpResponse serverHttpResponse = exchange.getResponse();
      serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
      DataBufferFactory dataBufferFactory = serverHttpResponse.bufferFactory();
      DataBuffer dataBuffer = dataBufferFactory.wrap("No Auth".getBytes(StandardCharsets.UTF_8));
      return serverHttpResponse.writeWith(Mono.just(dataBuffer));
    }
    return chain.filter(exchange);
  }
  
  @Override
  public int getOrder() {
    return 0;
  }
}
