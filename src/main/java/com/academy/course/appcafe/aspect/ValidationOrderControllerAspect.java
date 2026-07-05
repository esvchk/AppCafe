package com.academy.course.appcafe.aspect;

import com.academy.course.appcafe.dto.ProductOrderRequest;
import com.academy.course.appcafe.exception.OrderValidationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class ValidationOrderControllerAspect {

    @Before("execution(* com.academy.course.appcafe.controller.OrderController.addProductInOrder(..))")
    public void validateBefore(JoinPoint joinPoint) {
        ProductOrderRequest request = null;
        BindingResult result = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof ProductOrderRequest productOrderRequest) {
                request = productOrderRequest;
            } else if (arg instanceof BindingResult bindingResult) {
                result = bindingResult;
            }
        }
        if (result != null && result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            throw new OrderValidationException(request.getOrderId(), errorMessage);
        }
    }
}
