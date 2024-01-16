package com.zheng.codeservice.codesandbox;

import com.zheng.codeservice.codesandbox.impl.local.DockerCodeSandbox;
import com.zheng.codeservice.codesandbox.impl.local.LocalCodeSandbox;
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
      case "docker":
        return new DockerCodeSandbox();
      default:
        return new TestCodeSandbox();
    }
  }
}
