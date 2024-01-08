package com.zheng.codeservice.codesandbox.impl.mode;

import com.zheng.blogcommon.model.codesandbox.ExecutionResult;

import java.io.File;
import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/08/2024 - 10:43
 */
public interface ExecutionMode {
  
  List<ExecutionResult> executeCompiledFile(File userCodeFile, List<String> inputList);
  
}
