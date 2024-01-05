package com.zheng.blogcommon.model.codesandbox;

import lombok.Data;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/04/2024 - 21:17
 */
@Data
public class ExecutionMessage {
  
  private Integer exitValue;
  
  private String message;
  
  private String errorMessage;
  
  private Long executionTime;
  
  private Long memoryUsage;
}
