package com.zheng.blogcommon.constant;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 04/24/2023 - 21:04
 */
public interface UserConstant {
  
  /**
   * User login state
   */
  String USER_LOGIN_STATE = "user_login";
  
  /**
   * default user role: user
   */
  String DEFAULT_USER_ROLE = "user";
  
  /**
   * admin role
   */
  String ADMIN_ROLE = "admin";
  
  /**
   * account banned
   */
  String BAN_ROLE = "ban";
  
  /**
   * user attendance redis key
   */
  String USER_ATTENDANCE_KEY = "attendance:";
}
