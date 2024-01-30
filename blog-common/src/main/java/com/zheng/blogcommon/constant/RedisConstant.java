package com.zheng.blogcommon.constant;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/27/2024 - 22:05
 */
public interface RedisConstant {
  
  String USER_REGISTRATION = "lock:user:";
  String USER_ATTENDANCE_KEY = "attendance:";
  
  String QUESTION_ID = "question:";
  
  String COUPON_ORDER = "order:";
  String COUPON_STOCK = "coupon:stock:";
  
}
