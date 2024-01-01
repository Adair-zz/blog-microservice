package com.zheng.questionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zheng.blogcommon.model.dto.question.QuestionQueryRequest;
import com.zheng.blogcommon.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.blogcommon.model.vo.question.QuestionVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 张峥
* @description 针对表【question(question)】的数据库操作Service
* @createDate 2023-12-31 01:11:31
*/
public interface QuestionService extends IService<Question> {
  
  QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);
  
  Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest httpServletRequest);
}
