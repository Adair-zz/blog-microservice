package com.zheng.codeservice.codesandbox.impl.mode;

import java.util.Locale;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/08/2024 - 11:08
 */
public class ExecutionModeFactory {

  public static ExecutionMode createExecutionMode(String mode) {
    switch (mode.toLowerCase()) {
      case "acm":
        return new ACMExecution();
      case "commandline":
        return new CommandLineExecution();
      default:
        return new CommandLineExecution();
    }
  }
}
