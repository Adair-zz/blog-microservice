package com.zheng.questionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.blogcommon.model.dto.questionsubmit.SubmittedQuestionAddRequest;
import com.zheng.blogcommon.model.entity.SubmittedQuestion;
import com.zheng.blogcommon.model.entity.User;
import com.zheng.questionservice.service.SubmittedQuestionService;
import com.zheng.questionservice.mapper.SubmittedQuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 张峥
* @description 针对表【submitted_question(submitted question)】的数据库操作Service实现
* @createDate 2023-12-31 01:16:32
*/
@Service
public class SubmittedQuestionServiceImpl extends ServiceImpl<SubmittedQuestionMapper, SubmittedQuestion>
    implements SubmittedQuestionService{
  
  @Override
  public long doQuestionSubmit(SubmittedQuestionAddRequest submittedQuestionAddRequest, User loginUser) {
    return 0;
  }
}




