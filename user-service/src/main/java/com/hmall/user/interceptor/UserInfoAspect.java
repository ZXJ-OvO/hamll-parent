package com.hmall.user.interceptor;


import com.hmall.common.util.ThreadLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Aspect
@Component
public class UserInfoAspect {

    @Resource
    private HttpServletRequest httpServletRequest;


    @Around("@annotation(com.hmall.common.annotation.UserId)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {

        Object result = pjp.proceed();

        Long[] userId = (Long[]) pjp.getArgs();

        ThreadLocalUtil.setUserId(userId[0]);

        return result;
    }

}
