package com.zheng.blogcommon.model.dto.question;

import com.zheng.blogcommon.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/31/2023 - 14:04
 */
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {
  
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
   * user id
   */
  private Long userId;
  
  private static final long serialVersionUID = 1L;
}
