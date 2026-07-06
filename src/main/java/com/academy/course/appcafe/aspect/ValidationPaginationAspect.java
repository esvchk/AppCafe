package com.academy.course.appcafe.aspect;

import com.academy.course.appcafe.exception.PaginationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Component
@Aspect
public class ValidationPaginationAspect {
    @Before("@annotation(com.academy.course.appcafe.annotation.ValidPagination)")
    public void validatePage(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();


        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            String paramName = parameterNames[i];

            if (arg instanceof Integer) {
                int value = (Integer) arg;

                if ("page".equals(paramName) && value < 0) {
                    throw new PaginationException(
                            String.format("Page number '%s' must be positive or 0. Send: %d", paramName,value)
                    );
                } else if ("size".equals(paramName)&& value < 1) {
                    throw new IllegalArgumentException(
                            String.format("Page size '%s' must be positive. Send %d",paramName,value));
                }
            }
        }
    }
}
