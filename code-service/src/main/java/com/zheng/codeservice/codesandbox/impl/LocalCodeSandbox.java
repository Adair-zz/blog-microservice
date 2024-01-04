package com.zheng.codeservice.codesandbox.impl;

import com.zheng.blogcommon.model.codesandbox.CodeExecutionRequest;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionResponse;
import com.zheng.codeservice.codesandbox.CodeSandbox;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:28
 */
@Slf4j
public class LocalCodeSandbox implements CodeSandbox {
  @Override
  public CodeExecutionResponse executeCode(CodeExecutionRequest codeExecutionRequest) {
    log.info("Local Code Sandbox");
    return null;
  }
}
