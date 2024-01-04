package com.zheng.codeservice.strategy;

import com.zheng.blogcommon.model.dto.question.JudgeCase;
import com.zheng.blogcommon.model.entity.Question;
import com.zheng.blogcommon.model.enums.ExecutionInfoMessageEnum;
import com.zheng.blogcommon.model.codesandbox.ExecutionInfo;

import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:57
 */
public class DefaultJudgeStrategy implements JudgeStrategy {
  
  @Override
  public ExecutionInfo doJudge(ExecutionContext executionContext) {
    List<String> inputList = executionContext.getInputList();
    List<String> outputList = executionContext.getOutputList();
    Question question = executionContext.getQuestion();
    List<JudgeCase> judgeCaseList = executionContext.getJudgeCaseList();
    ExecutionInfoMessageEnum executionInfoMessageEnum = ExecutionInfoMessageEnum.ACCEPTED;
    ExecutionInfo executionInfoResponse = new ExecutionInfo();
    
    // compare output with expected values
    if (outputList.size() != inputList.size()) {
      executionInfoMessageEnum = executionInfoMessageEnum.WRONG;
      executionInfoResponse.setExecutionMessage(executionInfoMessageEnum.getValue());
      return executionInfoResponse;
    }
    
    for (int i = 0; i < judgeCaseList.size(); i++) {
      JudgeCase judgeCase = judgeCaseList.get(i);
      if (!judgeCase.getOutput().equals(outputList.get(i))) {
        executionInfoMessageEnum = executionInfoMessageEnum.WRONG;
        executionInfoResponse.setExecutionMessage(executionInfoMessageEnum.getValue());
        return executionInfoResponse;
      }
    }
    
    executionInfoResponse.setExecutionMessage(executionInfoMessageEnum.getValue());
    return executionInfoResponse;
  }
}
