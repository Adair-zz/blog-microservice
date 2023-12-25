package com.zheng.interviewservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.model.entity.InterviewQuestion;
import com.zheng.interviewservice.service.InterviewQuestionService;
import com.zheng.interviewservice.mapper.InterviewQuestionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
  public List<InterviewQuestion> getInterviewQuestionsByTopic(String topic) {
    if (StringUtils.isBlank(topic)) {
      throw new BusinessException(ErrorCode.PARAMS_ERROR, "The query parameter topic is empty");
    }
    QueryWrapper<InterviewQuestion> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("topic", topic);
    List<InterviewQuestion> interviewQuestions = this.list(queryWrapper);
    return interviewQuestions;
  }
}




