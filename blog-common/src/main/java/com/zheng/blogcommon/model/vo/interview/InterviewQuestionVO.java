package com.zheng.blogcommon.model.vo.interview;

import com.zheng.blogcommon.model.entity.InterviewQuestion;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/26/2023 - 23:18
 */
@Data
public class InterviewQuestionVO implements Serializable {
  
  /**
   * id
   */
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
  
  private static final long serialVersionUID = 2L;
}
