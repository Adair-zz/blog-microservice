package com.zheng.questionservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.zheng.blogapi.client.UserFeignClient;
import com.zheng.blogcommon.common.BaseResponse;
import com.zheng.blogcommon.common.DeleteRequest;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.common.ResultUtils;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.exception.ThrowUtils;
import com.zheng.blogcommon.model.dto.question.JudgeCase;
import com.zheng.blogcommon.model.dto.question.QuestionAddRequest;
import com.zheng.blogcommon.model.dto.question.QuestionQueryRequest;
import com.zheng.blogcommon.model.dto.question.QuestionUpdateRequest;
import com.zheng.blogcommon.model.dto.questionsubmit.SubmittedQuestionAddRequest;
import com.zheng.blogcommon.model.entity.Question;
import com.zheng.blogcommon.model.entity.User;
import com.zheng.blogcommon.model.vo.question.QuestionVO;
import com.zheng.questionservice.service.QuestionService;
import com.zheng.questionservice.service.SubmittedQuestionService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/31/2023 - 1:19
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {
  
  @Resource
  private QuestionService questionService;
  
  @Resource
  private SubmittedQuestionService submittedQuestionService;
  
  @Resource
  private UserFeignClient userFeignClient;
  
  private final static Gson GSON = new Gson();
  
  /**
   * Add new question.
   *
   * @param questionAddRequest
   * @param httpServletRequest
   * @return
   */
  @PostMapping("/add")
  public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
  
    if (questionAddRequest == null) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
  
    String title = questionAddRequest.getTitle();
    List<String> tagsList = questionAddRequest.getTags();
    String content = questionAddRequest.getContent();
    String answer = questionAddRequest.getAnswer();
    List<JudgeCase> judgeCaseList = questionAddRequest.getJudgeCase();
    
    if (StringUtils.isAnyBlank(title, content, answer) || tagsList == null || judgeCaseList == null) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    String tags = GSON.toJson(tagsList);
    String judgeCase = GSON.toJson(judgeCaseList);
  
    Question newQuestion = new Question();
    newQuestion.setTitle(title);
    newQuestion.setTags(tags);
    newQuestion.setContent(content);
    newQuestion.setAnswer(answer);
    newQuestion.setJudgeCase(judgeCase);
    newQuestion.setUserId(loginUser.getId());
    boolean isSave = questionService.save(newQuestion);
    ThrowUtils.throwIf(!isSave, ErrorCode.OPERATION_ERROR);
    return ResultUtils.success(newQuestion.getId());
  }
  
  /**
   * delete question.
   *
   * @param deleteRequest
   * @param httpServletRequest
   * @return
   */
  @PostMapping("/delete")
  public BaseResponse<Boolean> deleteQuestionById(@RequestBody DeleteRequest deleteRequest, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
  
    if (deleteRequest == null || deleteRequest.getId() <= 0) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
  
    long id = deleteRequest.getId();
  
    Question oldQuestion = questionService.getById(id);
    ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
  
    // only allow for users themselves or admin
    if (!oldQuestion.getUserId().equals(loginUser.getId()) && !userFeignClient.isAdmin(loginUser)) {
      throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
    }
  
    boolean isDelete = questionService.removeById(id);
    ThrowUtils.throwIf(!isDelete, ErrorCode.OPERATION_ERROR);
    return ResultUtils.success(isDelete);
  }
  
  /**
   * update question by user himself.
   *
   * @param questionUpdateRequest
   * @param httpServletRequest
   * @return
   */
  @PostMapping("/update")
  public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
    
    if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    Question question = new Question();
    BeanUtils.copyProperties(questionUpdateRequest, question);
    // check if the question exists
    long id = questionUpdateRequest.getId();
    Question oldQuestion = questionService.getById(id);
    // only allow for users themselves or admin
    if (!oldQuestion.getUserId().equals(loginUser.getId()) && !userFeignClient.isAdmin(loginUser)) {
      throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
    }
    ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
    Boolean isUpdate = questionService.updateById(question);
    return ResultUtils.success(isUpdate);
  }
  
  /**
   * get my question list by page.
   *
   * @param questionQueryRequest
   * @param httpServletRequest
   * @return
   */
  @PostMapping("/my/list/page/vo")
  public BaseResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
    
    if (questionQueryRequest == null) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    
    questionQueryRequest.setUserId(loginUser.getId());
    long currentPage = questionQueryRequest.getCurrentPage();
    long size = questionQueryRequest.getPageSize();
    ThrowUtils.throwIf(size > 30, ErrorCode.PARAMS_ERROR);
    Page<Question> questionPage = questionService.page(new Page<>(currentPage, size), questionService.getQueryWrapper(questionQueryRequest));
    return ResultUtils.success(questionService.getQuestionVOPage(questionPage, httpServletRequest));
  }
  
  /**
   * get question vo by id.
   *
   * @param id
   * @param httpServletRequest
   * @return
   */
  @GetMapping("/get/vo")
  public BaseResponse<QuestionVO> getQuestionVOById(long id, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
  
    if (id <= 0) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    Question question = questionService.getById(id);
    if (!question.getUserId().equals(loginUser.getId()) && !userFeignClient.isAdmin(loginUser)) {
      throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
    }
    QuestionVO questionVO = new QuestionVO();
    BeanUtils.copyProperties(question, questionVO);
    return ResultUtils.success(questionVO);
  }
  
  /**
   * submit question to execute the code.
   *
   * @param submittedQuestionAddRequest
   * @param httpServletRequest
   * @return
   */
  @PostMapping("/question_submit/do")
  public BaseResponse<Long> doQuestionSubmit(@RequestBody SubmittedQuestionAddRequest submittedQuestionAddRequest, HttpServletRequest httpServletRequest) {
    User loginUser = userFeignClient.getLoginUser(httpServletRequest);
  
    if (submittedQuestionAddRequest == null || submittedQuestionAddRequest.getQuestionId() <= 0) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    
    long submittedQuestionId = submittedQuestionService.doQuestionSubmit(submittedQuestionAddRequest, loginUser);
    return ResultUtils.success(submittedQuestionId);
  }
  
}
