package com.zheng.blogapi.client;

import com.zheng.blogcommon.model.entity.SubmittedQuestion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 23:19
 */
@FeignClient(value = "code-service", path = "/api/code/inner")
public interface CodeFeignClient {
  
  /**
   *
   * @param submittedQuestionId
   * @return
   */
  @PostMapping("/do")
  SubmittedQuestion doExecution(@RequestParam("submittedQuestionId") Long submittedQuestionId);
}
