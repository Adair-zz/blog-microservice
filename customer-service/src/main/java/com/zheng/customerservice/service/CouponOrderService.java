package com.zheng.customerservice.service;

import com.zheng.blogcommon.model.entity.CouponOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 张峥
 * @description 针对表【coupon_order(coupon order)】的数据库操作Service
 * @createDate 2024-01-23 16:59:45
 */
public interface CouponOrderService extends IService<CouponOrder> {
  
  Long createOrder(Long couponId, Long userId);
  
  Long createOrderAsync(Long couponId, Long userId);
}
