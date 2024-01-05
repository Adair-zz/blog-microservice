package com.zheng.codeservice.utils;

import com.zheng.blogcommon.model.codesandbox.ExecutionMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/04/2024 - 21:24
 */
@Slf4j
public class ProcessUtils {
  
  public static ExecutionMessage runProcess(Process process) {
    ExecutionMessage executionMessage = new ExecutionMessage();
    
    try {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      
      int exitValue = process.waitFor();
      executionMessage.setExitValue(exitValue);
  
      List<String> outputList = new ArrayList<>();
      if (exitValue == 0) {
        log.info("Succeed to compile user's code");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        
        String compileOutputLine;
        while ((compileOutputLine = bufferedReader.readLine()) != null) {
          outputList.add(compileOutputLine);
        }
        executionMessage.setMessage(StringUtils.join(outputList) + "\n");
      } else {
        log.info("Fail to compile user code");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String compileOutputLine;
        while ((compileOutputLine = bufferedReader.readLine()) != null) {
          outputList.add(compileOutputLine);
        }
        executionMessage.setMessage(StringUtils.join(outputList, "\n"));
        
        // error stream
        BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        List<String> errorOutputStrList = new ArrayList<>();
        String errorCompileOutputLine;
        while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
          errorOutputStrList.add(errorCompileOutputLine);
        }
        executionMessage.setErrorMessage(StringUtils.join(errorOutputStrList, "\n"));
      }
      
      stopWatch.stop();
      executionMessage.setExecutionTime(stopWatch.getLastTaskTimeMillis());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return executionMessage;
  }
}
