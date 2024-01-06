package com.zheng.codeservice.utils;

import com.zheng.blogcommon.model.codesandbox.ExecutionResult;
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
  
  public static ExecutionResult runProcess(Process process) {
    ExecutionResult executionResult = new ExecutionResult();
    
    try {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();
      
      int exitValue = process.waitFor();
      executionResult.setExitValue(exitValue);
  
      List<String> outputList = new ArrayList<>();
      if (exitValue == 0) {
        log.info("Succeed to compile user's code");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        
        String compileOutputLine;
        while ((compileOutputLine = bufferedReader.readLine()) != null) {
          outputList.add(compileOutputLine);
        }
        executionResult.setOutputList(outputList);
      } else {
        log.info("Fail to compile user code");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String compileOutputLine;
        while ((compileOutputLine = bufferedReader.readLine()) != null) {
          outputList.add(compileOutputLine);
        }
        executionResult.setOutputList(outputList);
        
        // error stream
        BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        List<String> errorOutputStrList = new ArrayList<>();
        String errorCompileOutputLine;
        while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
          errorOutputStrList.add(errorCompileOutputLine);
        }
        executionResult.setErrorMessage(StringUtils.join(errorOutputStrList, "\n"));
      }
      
      stopWatch.stop();
      // todo memory usage
      executionResult.setMemoryUsage(1000L);
      executionResult.setExecutionTime(stopWatch.getLastTaskTimeMillis());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return executionResult;
  }
}
