package com.zheng.blogcommon.model.codesandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeExecutionResponse {
  
  /**
   * output list.
   */
  private List<String> outputList;
  
  /**
   * interface message.
   */
  private String message;
  
  /**
   * status
   */
  private Integer status;
  
  /**
   * judge info
   */
  private ExecutionInfo executionInfo;
}
