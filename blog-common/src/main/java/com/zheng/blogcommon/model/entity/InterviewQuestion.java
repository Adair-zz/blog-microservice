package com.zheng.blogcommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * interview question
 * @TableName interview_question
 */
@TableName(value ="interview_question")
@Data
public class InterviewQuestion implements Serializable {
  /**
   * id
   */
  @TableId(type = IdType.AUTO)
  private Long id;
  
  /**
   * language
   */
  private String language;
  
  /**
   * topic group
   */
  private String topic;
  
  /**
   * interview question
   */
  private String question;
  
  /**
   * interview answer
   */
  private String answer;
  
  /**
   * user id
   */
  private Long userId;
  
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
  private Integer isDelete;
  
  @TableField(exist = false)
  private static final long serialVersionUID = 1L;
}