package com.zheng.customerservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.common.ResultUtils;
import com.zheng.blogcommon.constant.RedisConstant;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.exception.ThrowUtils;
import com.zheng.blogcommon.model.entity.Coupon;
import com.zheng.blogcommon.model.entity.CouponOrder;
import com.zheng.customerservice.service.CouponOrderService;
import com.zheng.customerservice.mapper.CouponOrderMapper;
import com.zheng.customerservice.service.CouponService;
import com.zheng.customerservice.utils.RedisLock;
import com.zheng.customerservice.utils.RedisUniqueID;
import jakarta.annotation.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author 张峥
 * @description 针对表【coupon_order(coupon order)】的数据库操作Service实现
 * @createDate 2024-01-23 16:59:45
 */
@Service
public class CouponOrderServiceImpl extends ServiceImpl<CouponOrderMapper, CouponOrder>
    implements CouponOrderService {
  
  @Resource
  private CouponService couponService;
  
  @Resource
  private RedisUniqueID redisUniqueID;
  
  @Resource
  private StringRedisTemplate stringRedisTemplate;
  
  private static DefaultRedisScript<Long> COUPON_ORDER_SCRIPT;
  
  static {
    COUPON_ORDER_SCRIPT = new DefaultRedisScript<>();
    COUPON_ORDER_SCRIPT.setLocation(new ClassPathResource("lua/coupon_order.lua"));
    COUPON_ORDER_SCRIPT.setResultType(Long.class);
  }
  
  @Override
  public Long createOrder(Long couponId, Long userId) {
    Coupon coupon = couponService.getById(couponId);
    if (coupon.getStock() < 1) {
      throw new BusinessException(ErrorCode.OPERATION_ERROR, "Inventory shortage");
    }
    
    // synchronized (userId.toString().intern())
    RedisLock lock = new RedisLock(RedisConstant.COUPON_ORDER + userId, stringRedisTemplate, 10);
    boolean isLock = lock.tryLock();
    ThrowUtils.throwIf(!isLock, ErrorCode.OPERATION_ERROR, "Duplicate orders are not allowed");
    
    try {
      Long count = this.query().eq("userId", userId).eq("couponId", couponId).count();
      ThrowUtils.throwIf(count > 0L, ErrorCode.OPERATION_ERROR, "You already have one coupon!");
      
      boolean isCouponUpdate = couponService.update()
          .setSql("stock = stock - 1")
          .eq("id", couponId)
//          .eq("stock", coupon.getStock()) //失败率高
          .gt("stock", 0)
          .update();
      ThrowUtils.throwIf(!isCouponUpdate, ErrorCode.OPERATION_ERROR);
      
      CouponOrder couponOrder = new CouponOrder();
      long orderId = redisUniqueID.generateId("order");
      couponOrder.setOrderId(orderId);
      couponOrder.setUserId(userId);
      couponOrder.setCouponId(couponId);
      this.save(couponOrder);
      return orderId;
    } finally {
      lock.unlock();
    }
  }
  
  @Override
  public Long createOrderAsync(Long couponId, Long userId) {
    Long result = stringRedisTemplate.execute(
        COUPON_ORDER_SCRIPT,
        Collections.emptyList(),
        couponId.toString(),
        userId.toString());
    int r = result.intValue();
    if (r != 0) {
      throw new BusinessException(ErrorCode.OPERATION_ERROR);
    }
    
    Long orderId = redisUniqueID.generateId("order");
    // todo: save orderId to message queue
    
    return null;
  }
}




