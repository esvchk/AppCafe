package com.academy.course.appcafe.aspect;

import com.academy.course.appcafe.exception.EmployeeValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class ValidationEmployeeControllerAspect {

    @Before("execution(* com.academy.course.appcafe.controller.EmployeeController.updateEmployee(..))")
    public void validateBefore(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()){
            if (arg instanceof BindingResult bindingResult && bindingResult.hasErrors()) {
                throw new EmployeeValidationException(bindingResult);
            }
        }
    }
}

