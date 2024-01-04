package com.zheng.codeservice.strategy;


import com.zheng.blogcommon.model.codesandbox.ExecutionInfo;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:48
 */
public interface JudgeStrategy {
  
  /**
   * code execution strategy
   *
   * @param judgeContext
   * @return
   */
  ExecutionInfo doJudge(ExecutionContext judgeContext);
}
