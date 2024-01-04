package com.zheng.userservice.controller.inner;

import com.zheng.blogapi.client.UserFeignClient;
import com.zheng.blogcommon.model.entity.User;
import com.zheng.userservice.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/26/2023 - 20:30
 */
@RestController
@RequestMapping("/user/inner")
public class UserInnerController implements UserFeignClient {
  
  @Resource
  private UserService userService;
  
  @Override
  @GetMapping("/get/id")
  public User getById(long userId) {
    return userService.getById(userId);
  }
  
  @Override
  @GetMapping("/get/ids")
  public List<User> listByIds(Collection<Long> idList) {
    return userService.listByIds(idList);
  }
}
