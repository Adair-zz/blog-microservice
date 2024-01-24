package com.zheng.blogcommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * coupon order
 *
 * @TableName coupon_order
 */
@TableName(value = "coupon_order")
@Data
public class CouponOrder implements Serializable {
  /**
   * primary key
   */
  @TableId
  private Long id;
  
  /**
   * user id
   */
  private Long userId;
  
  /**
   * coupon id
   */
  private Long coupon_id;
  
  /**
   * creation time
   */
  private Date createTime;
  
  /**
   * update time
   */
  private Date updateTime;
  
  /**
   * is delete
   */
  @TableLogic
  private Integer idDelete;
  
  @TableField(exist = false)
  private static final long serialVersionUID = 1L;
}