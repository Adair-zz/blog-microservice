package com.zheng.userservice.aop;

import com.zheng.blogcommon.annotation.AuthCheck;
import com.zheng.blogcommon.common.ErrorCode;
import com.zheng.blogcommon.exception.BusinessException;
import com.zheng.blogcommon.model.entity.User;
import com.zheng.blogcommon.model.enums.UserRoleEnum;
import com.zheng.userservice.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author: Zheng Zhang
 * @Description
 * @Created 04/29/2023 - 19:57
 */
@Aspect
@Component
public class AuthInterceptor {
  
  @Resource
  private UserService userService;
  
  @Around("@annotation(authCheck)")
  public Object doInterceptor(ProceedingJoinPoint proceedingJoinPoint, AuthCheck authCheck) throws Throwable {
    String mustRole = authCheck.mustRole();
    RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
    HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
    // Get current user
    User loginUser = userService.getLoginUser(httpServletRequest);
    // only pass if it has the role
    if (StringUtils.isNotBlank(mustRole)) {
      UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
      if (mustUserRoleEnum == null) {
        throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
      }
      String userRole = loginUser.getUserRole();
      // reject if role is ban
      if (UserRoleEnum.BAN.equals(mustUserRoleEnum)) {
        throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
      }
      // must have admin role
      if (UserRoleEnum.ADMIN.equals(mustUserRoleEnum)) {
        if (!mustRole.equals(userRole)) {
          throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
      }
    }
    return proceedingJoinPoint.proceed();
  }
}
