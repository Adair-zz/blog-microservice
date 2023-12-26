package com.zheng.interviewservice.controller;

import com.zheng.blogcommon.common.BaseResponse;
import com.zheng.blogcommon.common.DeleteRequest;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.common.ResultUtils;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.exception.ThrowUtils;
import com.zheng.blogcommon.model.dto.interview.InterviewQuestionAddRequest;
import com.zheng.blogcommon.model.dto.interview.InterviewQuestionQueryRequest;
import com.zheng.blogcommon.model.entity.InterviewQuestion;
import com.zheng.interviewservice.service.InterviewQuestionService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/24/2023 - 18:40
 */
@RestController
@RequestMapping("/")
public class InterviewController {
  
  @Resource
  private InterviewQuestionService interviewQuestionService;
  
  @PostMapping("add")
  public BaseResponse<Long> addInterviewQuestion(@RequestBody InterviewQuestionAddRequest interviewQuestionAddRequest) {
    if (interviewQuestionAddRequest == null) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    
    String language = interviewQuestionAddRequest.getLanguage();
    String topic = interviewQuestionAddRequest.getTopic();
    String question = interviewQuestionAddRequest.getQuestion();
    String answer = interviewQuestionAddRequest.getAnswer();
    if (StringUtils.isAnyBlank(language, topic, question, answer)) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
  
    InterviewQuestion newQuestion = new InterviewQuestion();
    BeanUtils.copyProperties(interviewQuestionAddRequest, newQuestion);
    boolean isSave = interviewQuestionService.save(newQuestion);
    ThrowUtils.throwIf(!isSave, ErrorCode.OPERATION_ERROR);
    return ResultUtils.success(newQuestion.getId());
  }
  
  @GetMapping("get/topic")
  public BaseResponse<List<InterviewQuestion>> getInterviewQuestionsByTopic(@RequestBody InterviewQuestionQueryRequest interviewQuestionQueryRequest) {
    if (interviewQuestionQueryRequest == null) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    
    String topic = interviewQuestionQueryRequest.getTopic();
    List<InterviewQuestion> interviewQuestionsByTopic = interviewQuestionService.getInterviewQuestionsByTopic(topic);
    if (interviewQuestionsByTopic == null) {
      throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
    }
    return ResultUtils.success(interviewQuestionsByTopic);
  }
  
  @GetMapping("/delete")
  public BaseResponse<Boolean> deleteInterviewQuestion(@RequestBody DeleteRequest deleteRequest) {
    if (deleteRequest == null || deleteRequest.getId() <= 0) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
  
    boolean isDelete = interviewQuestionService.removeById(deleteRequest.getId());
    ThrowUtils.throwIf(!isDelete, ErrorCode.OPERATION_ERROR);
    return ResultUtils.success(isDelete);
  }
  
}
