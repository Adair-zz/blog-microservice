package com.zheng.blogcommon.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 1:08
 */
public enum SubmittedQuestionStatusEnum {
  
  WAITING("wait", 0),
  RUNNING("running", 1),
  SUCCESS("success", 2),
  FAILURE("failure", 3);
  
  private final String status;
  
  private final Integer value;
  
  SubmittedQuestionStatusEnum(String status, Integer value) {
    this.status = status;
    this.value = value;
  }
  
  public static List<Integer> getValues() {
    return Arrays.stream(values()).map(entity -> entity.value).collect(Collectors.toList());
  }
  
  public static SubmittedQuestionStatusEnum getEnumByValue(Integer value) {
    if (ObjectUtils.isEmpty(value)) {
      return null;
    }
    for (SubmittedQuestionStatusEnum anEnum : SubmittedQuestionStatusEnum.values()) {
      if (anEnum.value.equals(value)) {
        return anEnum;
      }
    }
    return null;
  
  }
  
  public Integer getValue() {
    return value;
  }
  
  public String getStatus() {
    return status;
  }
  
}
