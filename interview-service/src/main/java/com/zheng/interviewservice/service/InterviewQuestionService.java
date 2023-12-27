package com.zheng.interviewservice.service;

import com.zheng.blogcommon.model.dto.interview.InterviewQuestionQueryRequest;
import com.zheng.blogcommon.model.entity.InterviewQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 张峥
* @description 针对表【interview_question(interview question)】的数据库操作Service
* @createDate 2023-12-24 18:55:16
*/
public interface InterviewQuestionService extends IService<InterviewQuestion> {

  List<InterviewQuestion> getByQueryRequest(InterviewQuestionQueryRequest interviewQuestionQueryRequest, Long userId);
}
