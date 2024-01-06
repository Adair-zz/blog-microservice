package com.zheng.codeservice.controller.inner;

import com.zheng.blogapi.client.CodeFeignClient;
import com.zheng.blogcommon.model.entity.SubmittedQuestion;
import com.zheng.codeservice.service.ExecutionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 14:59
 */
@RestController
@RequestMapping("/code/inner")
@Slf4j
public class CodeInnerController implements CodeFeignClient {
  
  @Resource
  private ExecutionService executionService;
  
  @Override
  @PostMapping("/do")
  public SubmittedQuestion doExecution(Long submittedQuestionId) {
    return executionService.doExecution(submittedQuestionId);
  }
}
