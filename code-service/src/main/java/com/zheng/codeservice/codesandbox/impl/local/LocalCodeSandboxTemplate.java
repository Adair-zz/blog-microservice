package com.zheng.codeservice.codesandbox.impl.local;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionRequest;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionResponse;
import com.zheng.blogcommon.model.codesandbox.ExecutionInfo;
import com.zheng.blogcommon.model.codesandbox.ExecutionResult;
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
    ExecutionResult executionResult = compileFile(userCodeTempFile);
    log.info(executionResult.toString());
    
    // run compiled File
    List<ExecutionResult> executionResultList = runCompiledFile(userCodeTempFile, inputList);
    
    // get execution response
    CodeExecutionResponse codeExecutionResponse = getExecutionResponse(executionResultList);
    
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
  
  private ExecutionResult compileFile(File userCodeFile) {
    String compileCommand = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
    try {
      Process compileProcess = Runtime.getRuntime().exec(compileCommand);
      ExecutionResult executionResult = ProcessUtils.runProcess(compileProcess);
      if (executionResult.getExitValue() != 0) {
        throw new RuntimeException("Fail to compile user code");
      }
      return executionResult;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private List<ExecutionResult> runCompiledFile(File userCodeFile, List<String> inputList) {
    String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
    
    List<ExecutionResult> executionResultList = new ArrayList<>();
    for (String inputArgs : inputList) {
      String runCommand = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath, inputArgs);
      try {
        Process process = Runtime.getRuntime().exec(runCommand);
        ExecutionResult executionResult = ProcessUtils.runProcess(process);
        executionResultList.add(executionResult);
      } catch (Exception e) {
        throw new RuntimeException("Fail to run compiled code", e);
      }
    }
    return executionResultList;
  }
  
  public CodeExecutionResponse getExecutionResponse(List<ExecutionResult> executionResultList) {
    CodeExecutionResponse codeExecutionResponse = new CodeExecutionResponse();
    List<String> outputList = new ArrayList<>();
    
    long maxTime = 0;
    for (ExecutionResult executionResult : executionResultList) {
      String errorMessage = executionResult.getErrorMessage();
      if (StrUtil.isNotBlank(errorMessage)) {
        codeExecutionResponse.setMessage(errorMessage);
        codeExecutionResponse.setStatus(SubmittedQuestionStatusEnum.FAILURE.getValue());
        break;
      }
      outputList = executionResult.getOutputList();
      Long time = executionResult.getExecutionTime();
      if (time != null) {
        maxTime = Math.max(maxTime, time);
      }
    }
    
    if (outputList.size() == executionResultList.size()) {
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
      boolean isDelete = FileUtil.del(fileParentPath);
      return isDelete;
    }
    return true;
  }
}
