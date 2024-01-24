package com.zheng.blogcommon.model.dto.coupon;

import lombok.Data;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/23/2024 - 17:24
 */
@Data
public class CouponAddRequest {
  
  /**
   * voucher title
   */
  private String title;
  
  /**
   * voucher stock
   */
  private Integer stock;
  
}
