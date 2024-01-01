package com.zheng.blogcommon.model.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/31/2023 - 12:07
 */
@Data
public class QuestionUpdateRequest implements Serializable {
  
  /**
   * id
   */
  private Long id;
  
  /**
   * title
   */
  private String title;
  
  /**
   * content
   */
  private String content;
  
  /**
   * tag list (json)
   */
  private List<String> tags;
  
  /**
   * question answer
   */
  private String answer;
  
  /**
   * judge case (json)
   */
  private List<JudgeCase> judgeCase;
  
  private static final long serialVersionUID = 1L;
}
