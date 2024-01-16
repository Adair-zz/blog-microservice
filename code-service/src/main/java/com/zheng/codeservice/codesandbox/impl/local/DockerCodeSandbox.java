package com.zheng.codeservice.codesandbox.impl.local;

import cn.hutool.core.util.ArrayUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionRequest;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionResponse;
import com.zheng.blogcommon.model.codesandbox.ExecutionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/15/2024 - 21:13
 */
@Slf4j
public class DockerCodeSandbox extends LocalCodeSandboxTemplate {
  
  private final String JAVA_DOCKER_IMAGE = "openjdk:17-alpine";
  
  private final boolean FIRST_INIT = true;
  
  private final Long TIMEOUT = 10000L;
  
  @Override
  public CodeExecutionResponse executeCode(CodeExecutionRequest codeExecutionRequest) {
    return super.executeCode(codeExecutionRequest);
  }
  
  @Override
  public List<ExecutionResult> runCompiledFile(String mode, File userCodeTempFile, List<String> inputList) {
    String userCodeParentPath = userCodeTempFile.getParentFile().getAbsolutePath();
    // default docker client
    DockerClient dockerClient = DockerClientBuilder.getInstance().build();
    
    if (FIRST_INIT) {
      PullImageCmd pullImageCmd = dockerClient.pullImageCmd(JAVA_DOCKER_IMAGE);
      PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
        @Override
        public void onNext(PullResponseItem item) {
          log.info("Java Docker Image First Init");
          super.onNext(item);
        }
      };
      try {
        pullImageCmd.exec(pullImageResultCallback).awaitCompletion();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    log.info("Succeed to download java docker image");
    
    CreateContainerCmd containerCmd = dockerClient.createContainerCmd(JAVA_DOCKER_IMAGE);
    HostConfig hostConfig = new HostConfig();
    hostConfig.withMemory(100 * 1024 * 1024L);
    hostConfig.withMemorySwap(0L);
    hostConfig.withCpuCount(1L);
    hostConfig.setBinds(new Bind(userCodeParentPath, new Volume("/app")));
    CreateContainerResponse createContainerResponse = containerCmd
        .withHostConfig(hostConfig)
        .withNetworkDisabled(true)
        .withAttachStdin(true)
        .withAttachStderr(true)
        .withAttachStdout(true)
        .withTty(true)
        .exec();
    log.info(createContainerResponse.toString());
    String containerId = createContainerResponse.getId();
    
    // start container
    dockerClient.startContainerCmd(containerId).exec();
    
    List<ExecutionResult> executionResultList = new ArrayList<>();
    for (String inputArgs : inputList) {
      StopWatch stopWatch = new StopWatch();
      String[] inputArgsArray = inputArgs.split(" ");
      String[] cmdArray = ArrayUtil.append(new String[]{"java", "-cp", "/app", "Main"}, inputArgsArray);
      ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
          .withCmd(cmdArray)
          .withAttachStderr(true)
          .withAttachStdin(true)
          .withAttachStdout(true)
          .exec();
      
      ExecutionResult executionResult = new ExecutionResult();
      final String[] errorMessage = {null};
      final String[] message = {null};
      long time = 0L;
      final long[] maxMemoryUsage = {0L};
      
      String executionId = execCreateCmdResponse.getId();
      
      ExecStartResultCallback execStartResultCallback = new ExecStartResultCallback() {
        @Override
        public void onComplete() {
          super.onComplete();
        }
        
        @Override
        public void onNext(Frame frame) {
          StreamType streamType = frame.getStreamType();
          if (StreamType.STDERR.equals(streamType)) {
            errorMessage[0] = new String(frame.getPayload());
            log.info("Error on java docker result:", errorMessage[0]);
          } else {
            message[0] = new String(frame.getPayload());
            log.info("Java docker result:", message[0]);
          }
          super.onNext(frame);
        }
      };
      
      StatsCmd statsCmd = dockerClient.statsCmd(containerId);
      ResultCallback<Statistics> statisticsResultCallback = statsCmd.exec(new ResultCallback<Statistics>() {
        @Override
        public void onStart(Closeable closeable) {
        
        }
        
        @Override
        public void onNext(Statistics statistics) {
          log.info("Java docker memory usage:" + statistics.getMemoryStats().getUsage());
          maxMemoryUsage[0] = Math.max(statistics.getMemoryStats().getUsage(), maxMemoryUsage[0]);
        }
        
        @Override
        public void onError(Throwable throwable) {
        
        }
        
        @Override
        public void onComplete() {
        
        }
        
        @Override
        public void close() throws IOException {
        
        }
      });
      statsCmd.exec(statisticsResultCallback);
      
      try {
        stopWatch.start();
        dockerClient.execStartCmd(executionId)
            .exec(execStartResultCallback)
            .awaitCompletion(TIMEOUT, TimeUnit.MILLISECONDS);
        stopWatch.stop();
        time = stopWatch.getLastTaskTimeMillis();
        statsCmd.close();
      } catch (InterruptedException e) {
        log.info("Error: Java docker execution failed");
        throw new RuntimeException(e);
      }
      
      executionResult.setErrorMessage(errorMessage[0]);
      executionResult.setOutputList(List.of(message[0]));
      executionResult.setExecutionTime(time);
      executionResult.setMemoryUsage(maxMemoryUsage[0]);
      executionResultList.add(executionResult);
    }
    return executionResultList;
  }
}
