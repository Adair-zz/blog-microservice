package com.zheng.codeservice.codesandbox.impl;

import com.zheng.blogcommon.model.codesandbox.CodeExecutionRequest;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionResponse;
import com.zheng.blogcommon.model.enums.SubmittedQuestionStatusEnum;
import com.zheng.codeservice.codesandbox.CodeSandbox;
import com.zheng.blogcommon.model.codesandbox.ExecutionInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:25
 */
@Slf4j
public class TestCodeSandbox implements CodeSandbox {
  
  @Override
  public CodeExecutionResponse executeCode(CodeExecutionRequest codeExecutionRequest) {
    List<String> inputList = codeExecutionRequest.getInputList();
    CodeExecutionResponse codeExecutionResponse = new CodeExecutionResponse();
    codeExecutionResponse.setOutputList(inputList);
    codeExecutionResponse.setMessage("success");
    codeExecutionResponse.setStatus(SubmittedQuestionStatusEnum.SUCCESS.getValue());
    
    ExecutionInfo executionInfo = new ExecutionInfo();
    executionInfo.setExecutionMessage("accept");
    executionInfo.setMemoryUsage(100L);
    executionInfo.setExecutionTime(200L);
    
    codeExecutionResponse.setExecutionInfo(executionInfo);
    return codeExecutionResponse;
  }
  
}
