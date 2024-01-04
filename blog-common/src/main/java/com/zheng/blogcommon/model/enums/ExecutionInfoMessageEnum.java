package com.zheng.blogcommon.model.enums;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 01/03/2024 - 16:13
 */
public enum ExecutionInfoMessageEnum {
  
  ACCEPTED("success", "Accepted"),
  WRONG("failure", "Wrong Answer");

  private final String message;
  
  private final String value;
  
  ExecutionInfoMessageEnum(String message, String value) {
    this.message = message;
    this.value = value;
  }
  
  /**
   * get list of execution info message.
   * @return
   */
  public static List<String> getValues() {
    return Arrays.stream(values()).map(entity -> entity.value).collect(Collectors.toList());
  }
  
  public ExecutionInfoMessageEnum getEnumByValue(String value) {
    if (ObjectUtils.isEmpty(value)) {
      return null;
    }
    for (ExecutionInfoMessageEnum entity : ExecutionInfoMessageEnum.values()) {
      if (entity.value.equals(value)) {
        return entity;
      }
    }
    return null;
  }
  
  public String getMessage() {
    return message;
  }
  
  public String getValue() {
    return value;
  }
  
}
