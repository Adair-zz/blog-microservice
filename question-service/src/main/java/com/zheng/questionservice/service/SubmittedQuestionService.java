package com.zheng.questionservice.service;

import com.zheng.blogcommon.model.dto.questionsubmit.SubmittedQuestionAddRequest;
import com.zheng.blogcommon.model.entity.SubmittedQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.blogcommon.model.entity.User;

/**
* @author 张峥
* @description 针对表【submitted_question(submitted question)】的数据库操作Service
* @createDate 2023-12-31 01:16:32
*/
public interface SubmittedQuestionService extends IService<SubmittedQuestion> {

  long doQuestionSubmit(SubmittedQuestionAddRequest submittedQuestionAddRequest, User loginUser);
}
