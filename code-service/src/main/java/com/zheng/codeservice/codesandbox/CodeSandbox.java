package com.zheng.codeservice.codesandbox;

import com.zheng.blogcommon.model.codesandbox.CodeExecutionRequest;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionResponse;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:13
 */
public interface CodeSandbox {
  
  CodeExecutionResponse executeCode(CodeExecutionRequest codeExecutionRequest);
}
