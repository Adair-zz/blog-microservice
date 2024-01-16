package com.zheng.codeservice.codesandbox.impl.local;

import com.zheng.blogcommon.model.codesandbox.CodeExecutionRequest;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionResponse;
import com.zheng.blogcommon.model.codesandbox.ExecutionResult;
import com.zheng.codeservice.codesandbox.CodeSandbox;
import com.zheng.codeservice.codesandbox.impl.mode.CodeExecutionMode;
import com.zheng.codeservice.codesandbox.impl.mode.NativeExecutionModeFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:28
 */
@Slf4j
public class LocalCodeSandbox extends LocalCodeSandboxTemplate {
  
  @Override
  public CodeExecutionResponse executeCode(CodeExecutionRequest codeExecutionRequest) {
    return super.executeCode(codeExecutionRequest);
  }
  
  @Override
  public List<ExecutionResult> runCompiledFile(String mode, File userCodeTempFile, List<String> inputList) {
    CodeExecutionMode executionMode = NativeExecutionModeFactory.createExecutionMode(mode);
    List<ExecutionResult> executionResultList = executionMode.executeCompiledFile(userCodeTempFile, inputList);
    return executionResultList;
  }
}
