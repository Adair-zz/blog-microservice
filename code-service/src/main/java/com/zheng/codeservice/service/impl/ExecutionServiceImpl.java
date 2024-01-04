package com.zheng.codeservice.service.impl;

import cn.hutool.json.JSONUtil;
import com.zheng.blogapi.client.QuestionFeignClient;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionRequest;
import com.zheng.blogcommon.model.codesandbox.CodeExecutionResponse;
import com.zheng.blogcommon.model.codesandbox.ExecutionInfo;
import com.zheng.blogcommon.model.dto.question.JudgeCase;
import com.zheng.blogcommon.model.entity.Question;
import com.zheng.blogcommon.model.entity.SubmittedQuestion;
import com.zheng.blogcommon.model.enums.SubmittedQuestionStatusEnum;
import com.zheng.codeservice.codesandbox.CodeSandbox;
import com.zheng.codeservice.codesandbox.CodeSandboxFactory;
import com.zheng.codeservice.manager.JudgeManager;
import com.zheng.codeservice.service.ExecutionService;
import com.zheng.codeservice.strategy.ExecutionContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 21:47
 */
@Service
@Slf4j
public class ExecutionServiceImpl implements ExecutionService {
  
  @Resource
  private QuestionFeignClient questionFeignClient;
  
  @Resource
  private JudgeManager judgeManager;
  
  @Value("${codesandbox.type:test}")
  private String type;
  
  @Override
  public SubmittedQuestion doExecution(Long submittedQuestionId) {
    log.info("execution service implementation");
    SubmittedQuestion submittedQuestion = questionFeignClient.getSubmittedQuestionById(submittedQuestionId);
    if (submittedQuestion == null) {
      throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "Not found submitted question");
    }
    
    Long questionId = submittedQuestion.getQuestionId();
    Question question = questionFeignClient.getQuestionById(questionId);
    if (question == null) {
      throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "Not found question");
    }
    
    // check submitted question status
    if (!submittedQuestion.getStatus().equals(SubmittedQuestionStatusEnum.WAITING.getValue())) {
      throw new BusinessException(ErrorCode.OPERATION_ERROR, "Submitted question is waiting for execution");
    }
    
    // change submitted question status
    SubmittedQuestion submittedQuestionUpdate = new SubmittedQuestion();
    submittedQuestionUpdate.setId(submittedQuestionId);
    submittedQuestionUpdate.setStatus(SubmittedQuestionStatusEnum.RUNNING.getValue());
    boolean isUpdate = questionFeignClient.updateSubmittedQuestionById(submittedQuestionUpdate);
    if (!isUpdate) {
      throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Fail to update submitted question status");
    }
    
    // call code sandbox
    CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
    String language = submittedQuestion.getLanguage();
    String code = submittedQuestion.getCode();
    String judgeCaseStr = question.getJudgeCase();
    List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
    List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
    CodeExecutionRequest codeExecutionRequest = CodeExecutionRequest.builder()
        .code(code)
        .language(language)
        .inputList(inputList)
        .build();
  
    CodeExecutionResponse codeExecutionResponse = codeSandbox.executeCode(codeExecutionRequest);
    List<String> outputList = codeExecutionResponse.getOutputList();
  
    ExecutionContext executionContext = new ExecutionContext();
    executionContext.setSubmittedQuestion(submittedQuestion);
    executionContext.setQuestion(question);
    executionContext.setInputList(inputList);
    executionContext.setOutputList(outputList);
    executionContext.setJudgeCaseList(judgeCaseList);
    executionContext.setJudgeInfo(executionContext.getJudgeInfo());
    // get execution info
    ExecutionInfo executionInfo = judgeManager.doJudge(executionContext);
    
    // update submitted question info
    SubmittedQuestion submittedQuestionFinalUpdate = new SubmittedQuestion();
    submittedQuestionFinalUpdate.setId(submittedQuestionId);
    submittedQuestionFinalUpdate.setStatus(SubmittedQuestionStatusEnum.SUCCESS.getValue());
    submittedQuestionFinalUpdate.setJudgeInfo(JSONUtil.toJsonStr(executionInfo));
    isUpdate = questionFeignClient.updateSubmittedQuestionById(submittedQuestionFinalUpdate);
    if (!isUpdate) {
      throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Fail to update final submitted question info");
    }
    SubmittedQuestion submittedQuestionResult = questionFeignClient.getSubmittedQuestionById(submittedQuestionId);
    return submittedQuestionResult;
  }
}
