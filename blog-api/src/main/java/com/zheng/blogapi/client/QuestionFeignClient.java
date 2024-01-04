package com.zheng.blogapi.client;

import com.zheng.blogcommon.model.entity.Question;
import com.zheng.blogcommon.model.entity.SubmittedQuestion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 21:51
 */
@FeignClient(value = "question-service", path = "/api/question/inner")
public interface QuestionFeignClient {
  
  /**
   * get question by id.
   *
   * @param questionId
   * @return
   */
  @GetMapping("/get/id")
  Question getQuestionById(@RequestParam("questionId") Long questionId);
  
  /**
   * get submitted question by id.
   *
   * @param submittedQuestionId
   * @return
   */
  @GetMapping("/question_submit/get/id")
  SubmittedQuestion getSubmittedQuestionById(@RequestParam("submittedQuestionId") Long submittedQuestionId);
  
  /**
   * update submitted question.
   *
   * @param submittedQuestion
   * @return
   */
  @PostMapping("/question_submit/update")
  boolean updateSubmittedQuestionById(@RequestBody SubmittedQuestion submittedQuestion);
}
