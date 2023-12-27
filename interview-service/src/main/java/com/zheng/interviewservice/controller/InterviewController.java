package com.zheng.interviewservice.controller;

import com.zheng.blogapi.client.UserFeignClient;
import com.zheng.blogcommon.common.BaseResponse;
import com.zheng.blogcommon.common.DeleteRequest;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.common.ResultUtils;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.exception.ThrowUtils;
import com.zheng.blogcommon.model.dto.interview.InterviewQuestionAddRequest;
import com.zheng.blogcommon.model.dto.interview.InterviewQuestionQueryRequest;
import com.zheng.blogcommon.model.entity.InterviewQuestion;
import com.zheng.blogcommon.model.entity.User;
import com.zheng.interviewservice.service.InterviewQuestionService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
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
  private UserFeignClient userFeignClient;
  
  @Resource
  private InterviewQuestionService interviewQuestionService;
  
  /**
   * add new interview question.
   *
   * @param interviewQuestionAddRequest
   * @param httpServletRequest
   * @return
   */
  @PostMapping("/add")
  public BaseResponse<Long> addInterviewQuestion(@RequestBody InterviewQuestionAddRequest interviewQuestionAddRequest, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
    
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
    newQuestion.setUserId(loginUser.getId());
    boolean isSave = interviewQuestionService.save(newQuestion);
    ThrowUtils.throwIf(!isSave, ErrorCode.OPERATION_ERROR);
    return ResultUtils.success(newQuestion.getId());
  }
  
  /**
   * get interview question by query request.
   *
   * @param interviewQuestionQueryRequest
   * @param httpServletRequest
   * @return
   */
  @GetMapping("/my/list/topic")
  public BaseResponse<List<InterviewQuestion>> getInterviewQuestionsByTopic(
      @RequestBody InterviewQuestionQueryRequest interviewQuestionQueryRequest, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
    
    if (interviewQuestionQueryRequest == null) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    
    List<InterviewQuestion> interviewQuestionsByTopic = interviewQuestionService.getByQueryRequest(interviewQuestionQueryRequest, loginUser.getId());
    if (interviewQuestionsByTopic == null || interviewQuestionsByTopic.size() <= 0) {
      throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
    }
    return ResultUtils.success(interviewQuestionsByTopic);
  }
  
  /**
   * delete interview question.
   *
   * @param deleteRequest
   * @return
   */
  @GetMapping("/delete")
  public BaseResponse<Boolean> deleteInterviewQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
    
    if (deleteRequest == null || deleteRequest.getId() <= 0) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
  
    long id = deleteRequest.getId();
    
    InterviewQuestion oldInterviewQuestion = interviewQuestionService.getById(id);
    ThrowUtils.throwIf(oldInterviewQuestion == null, ErrorCode.NOT_FOUND_ERROR);
    
    // only allow for users themselves or admin
    if (!oldInterviewQuestion.getUserId().equals(loginUser.getId()) && !userFeignClient.isAdmin(loginUser)) {
      throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
    }
    
    boolean isDelete = interviewQuestionService.removeById(id);
    ThrowUtils.throwIf(!isDelete, ErrorCode.OPERATION_ERROR);
    return ResultUtils.success(isDelete);
  }
  
}
