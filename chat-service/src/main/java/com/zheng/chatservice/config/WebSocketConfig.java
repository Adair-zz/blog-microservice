package com.zheng.chatservice.config;

import com.zheng.blogapi.client.UserFeignClient;
import com.zheng.blogcommon.constant.UserConstant;
import com.zheng.blogcommon.model.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/20/2024 - 14:13
 */
@Configuration
@Slf4j
public class WebSocketConfig extends ServerEndpointConfig.Configurator {
  
  @Resource
  private UserFeignClient userFeignClient;
  
  @Bean
  public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
  }
  
  @Override
  public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request.getHttpSession();
    User currentUser = userFeignClient.getLoginUser(httpServletRequest);
    sec.getUserProperties().put(UserConstant.USER_LOGIN_STATE, currentUser);
    super.modifyHandshake(sec, request, response);
  }
}
