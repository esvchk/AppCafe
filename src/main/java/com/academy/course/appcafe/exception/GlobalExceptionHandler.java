package com.academy.course.appcafe.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;




@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(NoResourceFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("errorCode", ex.getStatusCode());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleInternalServerError(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("errorCode", "INTERNAL_SERVER_ERROR");
        return "error/500";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("errorCode", "ACCESS_DENIED");
        return "error/403";
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(AuthenticationException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("errorCode", "UNAUTHORIZED");
        return "error/401";
    }


    @ExceptionHandler(EntityNotFoundByIdException.class)
    public String handleEmployeeNotFoundById(EntityNotFoundByIdException ex,
                                                 RedirectAttributes attributes){
        attributes.addFlashAttribute("searchError",ex.getMessage());
        return "redirect:/getEmployeePage";
    }


    @ExceptionHandler(EntityNotFoundByNameException.class)
    public String handleEmployeeNotFoundByName(EntityNotFoundByIdException ex,
                                         RedirectAttributes attributes){
        attributes.addFlashAttribute("searchError",ex.getMessage());
        return "redirect:/getEmployeePage";
    }


    @ExceptionHandler(HandlerMethodValidationException.class)
    public String handleValidationFailed(HandlerMethodValidationException ex,
                                                 RedirectAttributes attributes){
        String errorMessage = ex.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Not valid id");
        attributes.addFlashAttribute("validationError",errorMessage);
        return "redirect:/getEmployeePage";
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingAttribute(MissingServletRequestParameterException ex,
                                         RedirectAttributes attributes){
        String message = String.format("Cannot use action URL parameter '%s' is wrong or missing.", ex.getParameterName());
                attributes.addFlashAttribute("missingAttributeError",message);
        return "redirect:/getEmployeePage";
    }




}
