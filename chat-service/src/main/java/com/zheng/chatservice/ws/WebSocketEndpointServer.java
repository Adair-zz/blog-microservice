package com.zheng.chatservice.ws;

import com.zheng.blogapi.client.UserFeignClient;
import com.zheng.blogcommon.constant.UserConstant;
import com.zheng.blogcommon.model.entity.User;
import com.zheng.chatservice.config.WebSocketConfig;
import jakarta.annotation.Resource;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/19/2024 - 15:25
 */
@Slf4j
@Component
@ServerEndpoint(value = "/customer-service", configurator = WebSocketConfig.class)
public class WebSocketEndpointServer {
  
  @Resource
  private UserFeignClient userFeignClient;
  
  private static Map<Long, Session> sessionMap = new ConcurrentHashMap<>();
  
  private Session session;
  
  private User user;
  
  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    
    User currentUser = (User) session.getUserProperties().get(UserConstant.USER_LOGIN_STATE);
    this.user = currentUser;
    sessionMap.put(user.getId(), session);
    broadcastMessageToAdmin("new request from customer!");
    log.info(currentUser.getUserName() + " starts a websocket connection");
    try {
      session.getBasicRemote().sendText("Server received your connection");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @OnMessage
  public void onMessage(String message) {
    log.info("Message from " + user.getUserName());
    broadcastMessageToAdmin(message);
    try {
      session.getBasicRemote().sendText("Server received your message: " + message);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @OnClose
  public void onClose() {
    sessionMap.remove(user.getId());
    log.info(user.getUserName() + " has ended the websocket connection");
    try {
      session.getBasicRemote().sendText("Websocket connection is closed!");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  /**
   * broadcast message to all admin
   *
   * @param message
   */
  public void broadcastMessageToAdmin(String message) {
    for (Map.Entry<Long, Session> entry : sessionMap.entrySet()) {
      User user = userFeignClient.getById(entry.getKey());
      if (user.getUserRole().equals(UserConstant.ADMIN_ROLE) && entry.getValue().isOpen()) {
        try {
          session.getBasicRemote().sendText(user.getId() + ": " + message);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
  
  /**
   * get current online users.
   *
   * @return
   */
  public Set<Long> getOnlineUsers() {
    return sessionMap.keySet();
  }
}