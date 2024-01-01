package com.zheng.blogcommon.model.vo.question;

import cn.hutool.json.JSONUtil;
import com.zheng.blogcommon.model.dto.question.JudgeCase;
import com.zheng.blogcommon.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/31/2023 - 14:00
 */
@Data
public class QuestionVO implements Serializable {
  
  /**
   * id
   */
  private Long id;
  
  /**
   * title
   */
  private String title;
  
  /**
   * content
   */
  private String content;
  
  /**
   * tag list (json)
   */
  private List<String> tags;
  
  /**
   * question answer
   */
  private String answer;
  
  /**
   * user id
   */
  private Long userId;
  
  /**
   * creation time
   */
  private Date createTime;
  
  /**
   * update time
   */
  private Date updateTime;
  
  public static QuestionVO objectToVO(Question question) {
    if (question == null) {
      return null;
    }
    QuestionVO questionVO = new QuestionVO();
    BeanUtils.copyProperties(question, questionVO);
    List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
    questionVO.setTags(tagList);
    return questionVO;
  }
  
  private static final long serialVersionUID = 1L;
}
