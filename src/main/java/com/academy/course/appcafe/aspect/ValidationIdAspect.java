package com.academy.course.appcafe.aspect;

import com.academy.course.appcafe.exception.InvalidOrderIdException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ValidationIdAspect {

    @Before("@annotation(com.academy.course.appcafe.annotation.ValidId)")
    public void validateAllIds(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        if (parameterNames == null || args == null) return;

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];

            // Защита от NullPointerException
            if (arg == null || i >= parameterNames.length) continue;

            String paramName = parameterNames[i];

            // 1. Специфичная проверка конкретно для orderId (Соблюдаем SRP)
            if ("orderId".equals(paramName) && arg instanceof Long) {
                long value = (Long) arg;
                if (value <= 0) {
                    throw new InvalidOrderIdException(
                            String.format("Identifier 'orderId' must be positive or bigger than 0. Send: %d", value)
                    );
                }
            }
            // 2. Общая проверка для ВСЕХ ОСТАЛЬНЫХ Long идентификаторов (id, employeeId и т.д.)
            else if (arg instanceof Long) {
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
