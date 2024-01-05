package com.zheng.codeservice.codesandbox.impl.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionRequest;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionResponse;
import com.zheng.blogcommon.model.codesandbox.ExecutionInfo;
import com.zheng.blogcommon.model.codesandbox.ExecutionMessage;
import com.zheng.blogcommon.model.enums.SubmittedQuestionStatusEnum;
import com.zheng.codeservice.codesandbox.CodeSandbox;
import com.zheng.codeservice.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/04/2024 - 21:08
 */
@Slf4j
public class LocalCodeSandboxTemplate implements CodeSandbox {
  
  private static final String GLOBAL_CODE_DIR_NAME = "tempCode";
  
  private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";
  
  @Override
  public CodeExecutionResponse executeCode(CodeExecutionRequest codeExecutionRequest) {
    List<String> inputList = codeExecutionRequest.getInputList();
    String code = codeExecutionRequest.getCode();
    String language = codeExecutionRequest.getLanguage();
    
    // save code to a temp file
    File userCodeTempFile = saveCodeToFile(code);
    
    // compile user code file
    ExecutionMessage executionMessage = compileFile(userCodeTempFile);
    
    // run compiled File
    List<ExecutionMessage> executionMessageList = runCompiledFile(userCodeTempFile, inputList);
    
    // get execution response
    CodeExecutionResponse codeExecutionResponse = getExecutionResponse(executionMessageList);
    
    // delete temp file
    boolean isDelete = deleteFile(userCodeTempFile);
    if (!isDelete) {
      log.error("Delete user temp file error, userCodeTempFile = {}", userCodeTempFile.getAbsolutePath());
    }
  
    return codeExecutionResponse;
  }
  
  public File saveCodeToFile(String code) {
    String userDir = System.getProperty("user.dir");
    String globalCodePath = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
    
    // check if the dir name exists
    if (!FileUtil.exist(globalCodePath)) {
      FileUtil.mkdir(globalCodePath);
    }
    
    String userCodeParentPath = globalCodePath + File.separator + UUID.randomUUID();
    String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
    File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
    return userCodeFile;
  }
  
  private ExecutionMessage compileFile(File userCodeFile) {
    String compileCommand = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
    try {
      Process compileProcess = Runtime.getRuntime().exec(compileCommand);
      ExecutionMessage executionMessage = ProcessUtils.runProcess(compileProcess);
      if (executionMessage.getExitValue() != 0) {
        throw new RuntimeException("Fail to compile user code");
      }
      return executionMessage;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private List<ExecutionMessage> runCompiledFile(File userCodeFile, List<String> inputList) {
    String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
    
    List<ExecutionMessage> executionMessageList = new ArrayList<>();
    for (String inputArgs : inputList) {
      String runCommand = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath, inputArgs);
      try {
        Process process = Runtime.getRuntime().exec(runCommand);
        ExecutionMessage executionMessage = ProcessUtils.runProcess(process);
        executionMessageList.add(executionMessage);
      } catch (Exception e) {
        throw new RuntimeException("Fail to run compiled code", e);
      }
    }
    return executionMessageList;
  }
  
  public CodeExecutionResponse getExecutionResponse(List<ExecutionMessage> executionMessageList) {
    CodeExecutionResponse codeExecutionResponse = new CodeExecutionResponse();
    List<String> outputList = new ArrayList<>();
    
    long maxTime = 0;
    for (ExecutionMessage executionMessage : executionMessageList) {
      String errorMessage = executionMessage.getErrorMessage();
      if (StrUtil.isNotBlank(errorMessage)) {
        codeExecutionResponse.setMessage(errorMessage);
        codeExecutionResponse.setStatus(SubmittedQuestionStatusEnum.FAILURE.getValue());
        break;
      }
      outputList.add(executionMessage.getMessage());
      Long time = executionMessage.getExecutionTime();
      if (time != null) {
        maxTime = Math.max(maxTime, time);
      }
    }
    
    if (outputList.size() == executionMessageList.size()) {
      codeExecutionResponse.setStatus(SubmittedQuestionStatusEnum.SUCCESS.getValue());
    }
    codeExecutionResponse.setOutputList(outputList);
    ExecutionInfo executionInfo = new ExecutionInfo();
    executionInfo.setExecutionTime(maxTime);
    // todo: get the memory usage
    executionInfo.setMemoryUsage(0L);
    
    codeExecutionResponse.setExecutionInfo(executionInfo);
    return codeExecutionResponse;
  }
  
  public boolean deleteFile(File file) {
    if (file.getParentFile() != null) {
      String fileParentPath = file.getParentFile().getAbsolutePath();
      boolean isDelete = FileUtil.del(file);
      return isDelete;
    }
    return true;
  }
}
