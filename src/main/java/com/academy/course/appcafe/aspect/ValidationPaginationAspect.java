package com.academy.course.appcafe.aspect;

import com.academy.course.appcafe.exception.PaginationException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
@Aspect
public class ValidationPaginationAspect {
    @Before("@annotation(com.academy.course.appcafe.annotation.ValidPagination)")
    public void validatePage(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();


        if (parameterNames == null || args == null) return;

        String fallbackUrl = "/main";
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String uri = request.getRequestURI();

            if (uri.contains("/newOrderPage/")) {
                Map<String, String> pathVariables = (Map<String, String>) request.getAttribute
                        (HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                if (pathVariables != null && pathVariables.containsKey("orderId")) {
                    String orderId = pathVariables.get("orderId");
                    fallbackUrl = "/newOrderPage/" + orderId;
                } else  {
                    fallbackUrl = uri;
                }
            } else if (uri.contains("Employee")) {
                fallbackUrl = "/getEmployeePage";
            } else if (uri.contains("Order")) {
                fallbackUrl = "/getOrderPage";
            } else if (uri.contains("Product")) {
                fallbackUrl = "/getProductPage";
            }

            if (uri.contains("/showCategoryPage/")) {
                Map<String, String> pathVariables = (Map<String, String>) request.getAttribute
                        (HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

                if (pathVariables != null && pathVariables.containsKey("categoryId")) {
                    String categoryId = pathVariables.get("categoryId");
                    fallbackUrl = "/showCategoryPage/" + categoryId;
                } else {
                    fallbackUrl = uri;
                }
            } else if (uri.contains("Category")) {
                fallbackUrl = "/getCategoryPage";
            }
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                String paramName = parameterNames[i];

                if (arg instanceof Integer) {
                    int value = (Integer) arg;
                    if ("page".equals(paramName) && value < 0) {
                        throw new PaginationException(
                                String.format("Page number must be positive or 0. Send: %d", value), fallbackUrl);
                    } else if ("size".equals(paramName) && value < 1) {
                        throw new PaginationException(
                                String.format("Page size must be positive. Send: %d", value),fallbackUrl);
                    }
                }
            }
        }
    }
}
