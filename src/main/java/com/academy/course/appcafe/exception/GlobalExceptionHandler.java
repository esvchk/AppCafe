package com.academy.course.appcafe.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(NoResourceFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("errorCode", "NOT_FOUND");
                return "error/404";
    }
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleInternalServerError(Exception ex, Model model) {
//        model.addAttribute("message", ex.getMessage());
//        model.addAttribute("errorCode", "INTERNAL_SERVER_ERROR");
//        return "error/500";
//    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("errorCode", "ACCESS_DENIED");
        return "error/403";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("errorCode", "ACCESS_DENIED");
        return "error/401";
    }

}
