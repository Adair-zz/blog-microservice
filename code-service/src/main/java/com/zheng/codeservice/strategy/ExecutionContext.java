package com.zheng.codeservice.strategy;


import com.zheng.blogcommon.model.dto.question.JudgeCase;
import com.zheng.blogcommon.model.entity.Question;
import com.zheng.blogcommon.model.entity.SubmittedQuestion;
import com.zheng.blogcommon.model.codesandbox.ExecutionInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 15:49
 */
@Data
public class ExecutionContext {
  
  private ExecutionInfo judgeInfo;
  
  private List<String> inputList;
  
  private List<String> outputList;
  
  private List<JudgeCase> judgeCaseList;
  
  private Question question;
  
  private SubmittedQuestion submittedQuestion;
}
