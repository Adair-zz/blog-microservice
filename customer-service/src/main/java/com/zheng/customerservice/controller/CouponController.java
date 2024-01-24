package com.zheng.customerservice.controller;

import com.zheng.blogapi.client.UserFeignClient;
import com.zheng.blogcommon.annotation.AuthCheck;
import com.zheng.blogcommon.common.BaseResponse;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.common.ResultUtils;
import com.zheng.blogcommon.constant.UserConstant;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.exception.ThrowUtils;
import com.zheng.blogcommon.model.dto.coupon.CouponAddRequest;
import com.zheng.blogcommon.model.dto.coupon.CouponGetRequest;
import com.zheng.blogcommon.model.entity.Coupon;
import com.zheng.blogcommon.model.entity.User;
import com.zheng.customerservice.service.CouponOrderService;
import com.zheng.customerservice.service.CouponService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/23/2024 - 17:20
 */
@RestController
@RequestMapping("/customer/coupon")
public class CouponController {
  
  @Resource
  private CouponService couponService;
  
  @Resource
  private CouponOrderService couponOrderService;
  
  @Resource
  private UserFeignClient userFeignClient;
  
  @PostMapping("/add")
  public BaseResponse<Long> addCouponse(@RequestBody CouponAddRequest couponAddRequest, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
    boolean isAdmin = userFeignClient.isAdmin(loginUser);
    ThrowUtils.throwIf(!isAdmin, ErrorCode.NO_AUTH_ERROR);
    
    if (couponAddRequest == null) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    
    String title = couponAddRequest.getTitle();
    Integer stock = couponAddRequest.getStock();
    if (StringUtils.isAnyBlank(title) || stock <= 0) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    
    Coupon newCoupon = new Coupon();
    BeanUtils.copyProperties(couponAddRequest, newCoupon);
    boolean isSave = couponService.save(newCoupon);
    ThrowUtils.throwIf(!isSave, ErrorCode.OPERATION_ERROR);
    return ResultUtils.success(newCoupon.getId());
  }
  
  @PostMapping("/get")
  public BaseResponse<Long> getCoupon(CouponGetRequest couponGetRequest, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
    
    if (couponGetRequest == null) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    
    Long couponId = couponGetRequest.getCouponId();
    Long userId = loginUser.getId();
    
    Long orderId = couponOrderService.createOrder(couponId, userId);
    return ResultUtils.success(orderId);
  }
}
