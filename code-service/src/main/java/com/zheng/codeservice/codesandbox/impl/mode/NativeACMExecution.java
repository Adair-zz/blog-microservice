package com.zheng.codeservice.codesandbox.impl.mode;

import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.model.codesandbox.ExecutionResult;
import com.zheng.codeservice.utils.ProcessUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/08/2024 - 10:52
 */
@Slf4j
public class NativeACMExecution implements CodeExecutionMode {
  
  private static final long TIME_OUT = 10000L;
  
  @Override
  public List<ExecutionResult> executeCompiledFile(File userCodeFile, List<String> inputList) {
    List<ExecutionResult> executionResultList = new ArrayList<>();
    for (String inputArgs : inputList) {
      ProcessBuilder processBuilder = new ProcessBuilder("java", "-Xmx256m", "-Dfile.enconding=UTF-8", "-cp", userCodeFile.getParent(), "Main");
      processBuilder.redirectInput(ProcessBuilder.Redirect.PIPE);
      try {
        Process process = processBuilder.start();
        
        // time out control
        new Thread(() -> {
          try {
            Thread.sleep(TIME_OUT);
            log.info("ACM execution timeout");
            process.destroy();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }).start();
        
        process.getOutputStream().write(inputArgs.getBytes(StandardCharsets.UTF_8));
        process.getOutputStream().write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
        process.getOutputStream().close();
  
        ExecutionResult executionResult = ProcessUtils.runProcess(process);
  
        executionResultList.add(executionResult);
      } catch (Exception e) {
        throw new BusinessException(ErrorCode.OPERATION_ERROR, e.getMessage());
      }
    }
    return executionResultList;
  }
}
