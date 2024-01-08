package com.zheng.blogcommon.model.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeExecutionRequest {
  
  /**
   * input list
   */
  private List<String> inputList;
  
  /**
   * submitted code
   */
  private String code;
  
  /**
   * programming language
   */
  private String language;
  
  /**
   * execution mode
   */
  private String mode;
}
