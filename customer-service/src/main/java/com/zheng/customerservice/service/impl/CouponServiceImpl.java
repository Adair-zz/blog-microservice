package com.zheng.customerservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.blogcommon.model.entity.Coupon;
import com.zheng.customerservice.service.CouponService;
import com.zheng.customerservice.mapper.CouponMapper;
import org.springframework.stereotype.Service;

/**
 * @author 张峥
 * @description 针对表【coupon(coupon)】的数据库操作Service实现
 * @createDate 2024-01-23 02:23:29
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon>
    implements CouponService {
  
}




