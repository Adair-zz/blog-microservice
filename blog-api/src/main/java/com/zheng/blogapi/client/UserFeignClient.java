package com.zheng.blogapi.client;

import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.constant.UserConstant;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.model.entity.User;
import com.zheng.blogcommon.model.enums.UserRoleEnum;
import com.zheng.blogcommon.model.vo.user.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/18/2023 - 23:26
 */
@FeignClient(value = "user-service", path = "/api/user/inner")
public interface UserFeignClient {
  
  /**
   * get user by id.
   *
   * @param userId
   * @return
   */
  @GetMapping("/get/id")
  User getById(@RequestParam("userId") long userId);
  
  /**
   * get list of users by id.
   * @param idList
   * @return
   */
  @GetMapping("/get/ids")
  List<User> listByIds(@RequestParam("idList") Collection<Long> idList);
  
  /**
   * Get current login user.
   *
   * @param httpServletRequest
   * @return
   */
  default User getLoginUser(HttpServletRequest httpServletRequest) {
    User currentLoginUser = (User) httpServletRequest.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
    if (currentLoginUser == null || currentLoginUser.getId() == null) {
      throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
    }
    return currentLoginUser;
  }
  
  /**
   * check if user is admin.
   *
   * @param user
   * @return
   */
  default boolean isAdmin(User user) {
    return user != null && user.getUserRole().equals(UserRoleEnum.ADMIN.getValue());
  }
  
  /**
   * get user vo.
   *
   * @param user
   * @return
   */
  default UserVO getUserVo(User user) {
    if (user == null) {
      return null;
    }
    UserVO userVO = new UserVO();
    BeanUtils.copyProperties(user, userVO);
    return userVO;
  }
  
}
