package com.zheng.questionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.constant.CommonConstant;
import com.zheng.blogcommon.constant.RedisConstant;
import com.zheng.blogcommon.exception.ThrowUtils;
import com.zheng.blogcommon.model.dto.question.QuestionQueryRequest;
import com.zheng.blogcommon.model.entity.Question;
import com.zheng.blogcommon.model.vo.question.QuestionVO;
import com.zheng.blogcommon.utils.SqlUtils;
import com.zheng.questionservice.service.QuestionService;
import com.zheng.questionservice.mapper.QuestionMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 张峥
 * @description 针对表【question(question)】的数据库操作Service实现
 * @createDate 2023-12-31 01:11:31
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {
  
  @Resource
  private RedisTemplate redisTemplate;
  
  @Resource
  private Cache<Long, Question> questionCache;
  
  @Override
  public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
    
    QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
    if (questionQueryRequest == null) {
      return queryWrapper;
    }
    
    Long id = questionQueryRequest.getId();
    String title = questionQueryRequest.getTitle();
    List<String> tags = questionQueryRequest.getTags();
    String content = questionQueryRequest.getContent();
    String answer = questionQueryRequest.getAnswer();
    Long userId = questionQueryRequest.getUserId();
    String sortField = questionQueryRequest.getSortField();
    String sortOrder = questionQueryRequest.getSortOrder();
    
    queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
    queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
    if (!CollectionUtils.isEmpty(tags)) {
      for (String tag : tags) {
        queryWrapper.like("tags", "\"" + tag + "\"");
      }
    }
    queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
    queryWrapper.like(StringUtils.isNotBlank(answer), "answer", answer);
    queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
    queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.ASCENDING_ORDER), sortField);
    return queryWrapper;
  }
  
  @Override
  public Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest httpServletRequest) {
    List<Question> questionList = questionPage.getRecords();
    Page<QuestionVO> questionVOPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
    if (CollectionUtils.isEmpty(questionList)) {
      return questionVOPage;
    }
  
    List<QuestionVO> questionVOList = questionList.stream().map(question -> QuestionVO.objectToVO(question)).collect(Collectors.toList());
    questionVOPage.setRecords(questionVOList);
    return questionVOPage;
  }
  
  @Override
  public Question getQuestionById(long id) {
    Question question = questionCache.get(id, key -> {
      // check from redis
      Question questionFromRedis = (Question) redisTemplate.opsForValue().get(RedisConstant.QUESTION_ID + key);
      if (questionFromRedis != null) {
        return questionFromRedis;
      }
      // if not found in redis, check from database
      Question questionFromDb = this.getById(key);
      return questionFromDb;
    });
    // todo update redis data using MQ
    redisTemplate.opsForValue().set(RedisConstant.QUESTION_ID + id, question, 30L, TimeUnit.MINUTES);
    
    return question;
  }
  
  @Override
  public Boolean updateQuestion(Question question) {
    long id = question.getId();
    boolean isUpdate = this.updateById(question);
    ThrowUtils.throwIf(!isUpdate, ErrorCode.OPERATION_ERROR);
    
    // delete data from local cache and redis
    questionCache.invalidate(id);
    // todo delete data from redis using MQ
    redisTemplate.delete(RedisConstant.QUESTION_ID + id);
    
    return true;
  }
  
  @Override
  public boolean removeQuestionById(long id) {
    boolean isDelete = this.removeById(id);
    ThrowUtils.throwIf(!isDelete, ErrorCode.OPERATION_ERROR);
    
    // delete data from local cache and redis
    questionCache.invalidate(id);
    // todo delete data from redis using MQ
    redisTemplate.delete(RedisConstant.QUESTION_ID + id);
    
    return true;
  }
}




