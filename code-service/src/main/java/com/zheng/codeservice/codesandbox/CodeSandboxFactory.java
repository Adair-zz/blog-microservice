package com.zheng.codeservice.codesandbox;

import com.zheng.codeservice.codesandbox.impl.LocalCodeSandbox;
import com.zheng.codeservice.codesandbox.impl.TestCodeSandbox;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:13
 */
public class CodeSandboxFactory {
  
  public static CodeSandbox newInstance(String type) {
    switch(type) {
      case "test":
        return new TestCodeSandbox();
      case "local":
        return new LocalCodeSandbox();
      default:
        return new TestCodeSandbox();
    }
  }
}
