package com.zheng.codeservice.codesandbox.impl.mode;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/08/2024 - 11:08
 */
public class NativeExecutionModeFactory {
  
  public static CodeExecutionMode createExecutionMode(String mode) {
    switch (mode.toLowerCase()) {
      case "acm":
        return new NativeACMExecution();
      case "commandline":
        return new NativeCommandLineExecution();
      default:
        return new NativeCommandLineExecution();
    }
  }
}
