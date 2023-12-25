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
   * topic group
   */
  private String topic;
  
  private static final long serialVersionUID = 1L;
}
