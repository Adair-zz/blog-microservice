package com.zheng.blogcommon.model.dto.interview;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/30/2023 - 23:33
 */
@Data
public class InterviewQuestionUpdateRequest implements Serializable {
  
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
  
  private static final long serialVersionUID = 1L;
}
