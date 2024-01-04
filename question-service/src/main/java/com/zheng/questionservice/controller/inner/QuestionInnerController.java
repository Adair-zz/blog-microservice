package com.zheng.questionservice.controller.inner;

import com.zheng.blogapi.client.QuestionFeignClient;
import com.zheng.blogcommon.model.entity.Question;
import com.zheng.blogcommon.model.entity.SubmittedQuestion;
import com.zheng.questionservice.service.QuestionService;
import com.zheng.questionservice.service.SubmittedQuestionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 21:54
 */
@Slf4j
@RestController
@RequestMapping("/question/inner")
public class QuestionInnerController implements QuestionFeignClient {
  
  @Resource
  private QuestionService questionService;
  
  @Resource
  private SubmittedQuestionService submittedQuestionService;
  
  @Override
  @GetMapping("/get/id")
  public Question getQuestionById(@RequestParam("questionId") Long questionId) {
    log.info("get question by id");
    return questionService.getById(questionId);
  }
  
  @Override
  @GetMapping("/question_submit/get/id")
  public SubmittedQuestion getSubmittedQuestionById(@RequestParam("submittedQuestionId") Long submittedQuestionId) {
    return submittedQuestionService.getById(submittedQuestionId);
  }
  
  @Override
  @PostMapping("/question_submit/update")
  public boolean updateSubmittedQuestionById(SubmittedQuestion submittedQuestion) {
    return submittedQuestionService.updateById(submittedQuestion);
  }
  
}
