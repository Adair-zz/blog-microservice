package com.zheng.blogcommon.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 0:31
 */
public enum SubmittedQuestionLanguageEnum {
  
  JAVA("java", "java"),
  JAVASCRIPT("javascript", "javascript");
  
  private final String value;
  
  private final String language;
  
  SubmittedQuestionLanguageEnum( String value, String language) {
    this.value = value;
    this.language = language;
  }
  
  /**
   * get values.
   *
   * @return
   */
  public static List<String> getValues() {
    return Arrays.stream(values()).map(entity -> entity.value).collect(Collectors.toList());
  }
  
  /**
   * get value by enum.
   *
   * @param language
   * @return
   */
  public static SubmittedQuestionLanguageEnum getEnumByLanguage(String language) {
    if (ObjectUtils.isEmpty(language)) {
      return null;
    }
    
    for (SubmittedQuestionLanguageEnum entity : SubmittedQuestionLanguageEnum.values()) {
      if (entity.value.equals(language)) {
        return entity;
      }
    }
    return null;
  }
  
  public String getLanguage() {
    return language;
  }
  
  public String getValue() {
    return value;
  }
}
