package com.zheng.blogcommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * coupon
 *
 * @TableName coupon
 */
@TableName(value = "coupon")
@Data
public class Coupon implements Serializable {
  /**
   * id
   */
  @TableId(type = IdType.AUTO)
  private Long id;
  
  /**
   * voucher title
   */
  private String title;
  
  /**
   * voucher stock
   */
  private Integer stock;
  
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