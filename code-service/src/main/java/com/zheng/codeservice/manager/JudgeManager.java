package com.zheng.codeservice.manager;

import com.zheng.blogcommon.model.codesandbox.ExecutionInfo;
import com.zheng.blogcommon.model.entity.SubmittedQuestion;
import com.zheng.codeservice.strategy.DefaultJudgeStrategy;
import com.zheng.codeservice.strategy.ExecutionContext;
import com.zheng.codeservice.strategy.JudgeStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 22:56
 */
@Service
@Slf4j
public class JudgeManager {
  
  /**
   * do judge.
   *
   * @param executionContext
   * @return
   */
  public ExecutionInfo doJudge(ExecutionContext executionContext) {
    SubmittedQuestion submittedQuestion = executionContext.getSubmittedQuestion();
    String language = submittedQuestion.getLanguage();
    JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
    return judgeStrategy.doJudge(executionContext);
  }
}
