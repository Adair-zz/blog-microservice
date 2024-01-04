package com.zheng.codeservice.service;

import com.zheng.blogcommon.model.entity.SubmittedQuestion;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:03
 */
public interface ExecutionService {
  
  /**
   * code judge.
   *
   * @param submittedQuestionId
   * @return
   */
  SubmittedQuestion doExecution(Long submittedQuestionId);
}
