package com.zheng.interviewservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.model.dto.interview.InterviewQuestionQueryRequest;
import com.zheng.blogcommon.model.entity.InterviewQuestion;
import com.zheng.interviewservice.service.InterviewQuestionService;
import com.zheng.interviewservice.mapper.InterviewQuestionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 张峥
* @description 针对表【interview_question(interview question)】的数据库操作Service实现
* @createDate 2023-12-24 18:55:16
*/
@Service
public class InterviewQuestionServiceImpl extends ServiceImpl<InterviewQuestionMapper, InterviewQuestion>
    implements InterviewQuestionService{
  
  @Override
  public List<InterviewQuestion> getByQueryRequest(InterviewQuestionQueryRequest interviewQuestionQueryRequest, Long userId) {
    if (interviewQuestionQueryRequest == null) {
      return new ArrayList<>();
    }
  
    String language = interviewQuestionQueryRequest.getLanguage();
    String topic = interviewQuestionQueryRequest.getTopic();
    String question = interviewQuestionQueryRequest.getQuestion();
    String answer = interviewQuestionQueryRequest.getAnswer();
  
    QueryWrapper<InterviewQuestion> queryWrapper = new QueryWrapper<>();
    queryWrapper.like(StringUtils.isNotBlank(language), "language", language);
    queryWrapper.like(StringUtils.isNotBlank(topic), "topic", topic);
    queryWrapper.like(StringUtils.isNotBlank(question), "question", question);
    queryWrapper.like(StringUtils.isNotBlank(answer), "answer", answer);
    queryWrapper.eq("userId", userId);
    List<InterviewQuestion> interviewQuestionList = this.list(queryWrapper);
    return interviewQuestionList;
  }
}




