package com.academy.course.appcafe.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationIdAspect {

    @Before("@within(com.academy.course.appcafe.annotation.ValidId)")
    public void validateAllIds(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            String paramName = parameterNames[i];

            if (arg instanceof Long) {
                long idValue = (Long) arg;

                if (idValue <= 0) {
                    throw new IllegalArgumentException(
                            String.format("Identifier '%s' must be positive or bigger than 0. Send: %d", paramName, idValue)
                    );
                }
            }
        }
    }
}
