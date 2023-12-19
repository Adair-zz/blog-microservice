package com.zheng.blogapi.client;

import com.zheng.blogcommon.common.BaseResponse;
import com.zheng.blogcommon.model.vo.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 12/18/2023 - 23:26
 */
@FeignClient("user-service")
public interface UserClient {
  
  @GetMapping("/get/login")
  BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest httpServletRequest);
  
}
