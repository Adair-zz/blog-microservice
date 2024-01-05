package com.zheng.codeservice.codesandbox;

import com.zheng.blogcommon.model.codesandbox.CodeExecutionRequest;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/04/2024 - 20:57
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox {
  
  private final CodeSandbox codeSandbox;
  
  public CodeSandboxProxy(CodeSandbox codeSandbox) {
    this.codeSandbox = codeSandbox;
  }
  
  @Override
  public CodeExecutionResponse executeCode(CodeExecutionRequest codeExecutionRequest) {
    log.info("CodeSandbox request info: " + codeExecutionRequest.toString());
    CodeExecutionResponse codeExecutionResponse = codeSandbox.executeCode(codeExecutionRequest);
    log.info("CodeSandbox response info: " + codeExecutionResponse.toString());
    return null;
  }
}
