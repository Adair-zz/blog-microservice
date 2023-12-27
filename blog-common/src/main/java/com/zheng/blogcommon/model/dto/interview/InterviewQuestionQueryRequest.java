package com.zheng.blogcommon.model.dto.interview;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/24/2023 - 22:53
 */
@Data
public class InterviewQuestionQueryRequest implements Serializable {
  
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
  
  private static final long serialVersionUID = 1L;
}
