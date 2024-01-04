package com.zheng.blogcommon.model.codesandbox;

import lombok.Data;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 16:04
 */
@Data
public class ExecutionInfo {
  
  /**
   * execution message.
   */
  private String executionMessage;
  
  /**
   * memory usage.
   */
  private Long memoryUsage;
  
  /**
   * execution time
   */
  private Long executionTime;
}
