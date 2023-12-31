package com.zheng.questionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.blogcommon.model.entity.Question;
import com.zheng.questionservice.service.QuestionService;
import com.zheng.questionservice.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author 张峥
* @description 针对表【question(question)】的数据库操作Service实现
* @createDate 2023-12-31 01:11:31
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




