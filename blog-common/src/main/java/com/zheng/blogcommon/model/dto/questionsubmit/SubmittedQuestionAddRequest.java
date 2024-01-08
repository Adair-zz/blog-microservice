package com.zheng.blogcommon.model.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 0:20
 */
@Data
public class SubmittedQuestionAddRequest implements Serializable {
  
  /**
   * programming language
   */
  private String language;
  
  /**
   * user code
   */
  private String code;
  
  /**
   * question id
   */
  private Long questionId;
  
  /**
   * execution mode
   */
  private String mode;
  
  private static final long serialVersionUID = 1L;
}
