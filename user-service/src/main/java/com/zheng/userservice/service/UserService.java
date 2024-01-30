package com.zheng.userservice.service;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zheng.blogcommon.common.BaseResponse;
import com.zheng.blogcommon.model.dto.user.UserQueryRequest;
import com.zheng.blogcommon.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.blogcommon.model.vo.user.LoginUserVO;
import com.zheng.blogcommon.model.vo.user.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * User Service.
 *
* @author Zheng Zhang
* @description 针对表【user(user)】的数据库操作Service
* @createDate 2023-04-22 17:21:28
*/
public interface UserService extends IService<User> {
  
  /**
   * User register.
   *
   * @param userAccount user account
   * @param userPassword user password
   * @param confirmPassword user confirm password
   * @return new user id
   */
  long userRegister(String userAccount, String userPassword, String confirmPassword);
  
  /**
   * User login.
   *
   * @param userAccount user account
   * @param userPassword user password
   * @return user desensitized info
   */
  LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest);
  
  /**
   * Get current login user.
   *
   * @param httpServletRequest http servlet request
   * @return current login user
   */
  User getLoginUser(HttpServletRequest httpServletRequest);
  
  /**
   * Check if user is admin by http servlet request.
   *
   * @param httpServletRequest http servlet request
   * @return true: is admin / false: is not admin
   */
  boolean isAdmin(HttpServletRequest httpServletRequest);
  
  /**
   * Check if this user is admin by http servlet request.
   *
   * @param user user object
   * @return true: is admin / false: is not admin
   */
  boolean isAdmin(User user);
  
  /**
   * User logout.
   *
   * @param httpServletRequest http servlet request
   */
  boolean userLogout(HttpServletRequest httpServletRequest);
  
  /**
   * Get current login user desensitized user information.
   *
   * @param user user obj
   * @return desensitized user
   */
  LoginUserVO getLoginUserVO(User user);
  
  /**
   * Get user desensitized user information.
   *
   * @param user user obj
   * @return desensitized user
   */
  UserVO getUserVO(User user);
  
  /**
   * Get list of desensitized user.
   *
   * @param userList list of user obj
   * @return list of desensitized user
   */
  List<UserVO> getUserVO(List<User> userList);
  
  /**
   * Query user by request params.
   *
   * @param userQueryRequest user query request
   * @return
   */
  QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
  
  /**
   * remove user by id.
   *
   * @param id
   * @return
   */
  boolean removeUserById(Long id);
  
  /**
   * user take attendance.
   *
   * @return
   */
  Boolean takeAttendance(HttpServletRequest httpServletRequest);
  
  /**
   * count attendance for current month.
   *
   * @param httpServletRequest
   * @return
   */
  Integer attendanceCount(HttpServletRequest httpServletRequest);
  
}
