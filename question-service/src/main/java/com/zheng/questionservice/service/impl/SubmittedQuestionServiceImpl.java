package com.zheng.questionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.blogapi.client.CodeFeignClient;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.exception.ThrowUtils;
import com.zheng.blogcommon.model.dto.questionsubmit.SubmittedQuestionAddRequest;
import com.zheng.blogcommon.model.entity.Question;
import com.zheng.blogcommon.model.entity.SubmittedQuestion;
import com.zheng.blogcommon.model.entity.User;
import com.zheng.blogcommon.model.enums.SubmittedQuestionLanguageEnum;
import com.zheng.blogcommon.model.enums.SubmittedQuestionStatusEnum;
import com.zheng.questionservice.service.QuestionService;
import com.zheng.questionservice.service.SubmittedQuestionService;
import com.zheng.questionservice.mapper.SubmittedQuestionMapper;
import io.netty.util.concurrent.CompleteFuture;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
* @author 张峥
* @description 针对表【submitted_question(submitted question)】的数据库操作Service实现
* @createDate 2023-12-31 01:16:32
*/
@Service
@Slf4j
public class SubmittedQuestionServiceImpl extends ServiceImpl<SubmittedQuestionMapper, SubmittedQuestion>
    implements SubmittedQuestionService{
  
  @Resource
  private QuestionService questionService;
  
  @Resource
  private CodeFeignClient codeFeignClient;
  
  @Override
  public long doQuestionSubmit(SubmittedQuestionAddRequest submittedQuestionAddRequest, User loginUser) {
    String language = submittedQuestionAddRequest.getLanguage();
    SubmittedQuestionLanguageEnum languageEnum = SubmittedQuestionLanguageEnum.getEnumByLanguage(language);
    if (languageEnum == null) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR, "Don't support " + language);
    }
    
    long questionId = submittedQuestionAddRequest.getQuestionId();
    Question question = questionService.getById(questionId);
    if (question == null) {
      throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
    }
    
    long userId = loginUser.getId();
    SubmittedQuestion submittedQuestion = new SubmittedQuestion();
    submittedQuestion.setUserId(userId);
    submittedQuestion.setQuestionId(questionId);
    submittedQuestion.setLanguage(language);
    submittedQuestion.setCode(submittedQuestionAddRequest.getCode());
    submittedQuestion.setStatus(SubmittedQuestionStatusEnum.WAITING.getValue());
    submittedQuestion.setJudgeInfo("{}");
    boolean isSave = this.save(submittedQuestion);
    if (!isSave) {
      throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Fail to insert submitted question!");
    }
    
    long submittedQuestionId = submittedQuestion.getId();
    log.info(submittedQuestion.getId() + "");
    // todo message queue
    CompletableFuture.runAsync(() -> {
      log.info("执行代码");
      codeFeignClient.doExecution(submittedQuestionId);
    });
    return submittedQuestionId;
  }
}




