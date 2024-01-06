package com.zheng.blogcommon.model.codesandbox;

import lombok.Data;

import java.util.List;

/**
 *
 *
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/04/2024 - 21:17
 */
@Data
public class ExecutionResult {
  
  private Integer exitValue;
  
  private List<String> outputList;
  
  private String errorMessage;
  
  private Long executionTime;
  
  private Long memoryUsage;
}
