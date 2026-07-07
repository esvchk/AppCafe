package com.academy.course.appcafe.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(NoResourceFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", ex.getStatusCode());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleInternalServerError(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "INTERNAL_SERVER_ERROR");
        return "error/500";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "ACCESS_DENIED");
        return "error/403";
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(AuthenticationException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", "UNAUTHORIZED");
        return "error/401";
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(InvalidParameterException ex, Model model) {
        model.addAttribute("errorMessage","Wrong or missing parameter for page size");
        model.addAttribute("errorCode","BAD_REQUEST");
        return "error/400";
    }


    @ExceptionHandler(EntityNotFoundByIdException.class)
    public String handleEntityNotFoundById(EntityNotFoundByIdException ex,
                                             RedirectAttributes attributes,
                                           HttpServletRequest request) {
        attributes.addFlashAttribute("errorMessage", ex.getMessage());
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/main");
    }

    @ExceptionHandler(EntityNotFoundByNameException.class)
    public String handleEntityNotFoundByName(EntityNotFoundByNameException ex,
                                               RedirectAttributes attributes,
                                             HttpServletRequest request) {
        attributes.addFlashAttribute("errorMessage", ex.getMessage());
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/main");
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public String handleValidationFailed(HandlerMethodValidationException ex,
                                         RedirectAttributes attributes,HttpServletRequest request){
        String errorMessage = ex.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Not valid parameter");
        attributes.addFlashAttribute("errorMessage",errorMessage);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/main");
    }
    @ExceptionHandler(EmployeeValidationException.class)
    public String handleEmployeeValidation(EmployeeValidationException ex, Model model) {
        BindingResult result = ex.getBindingResult();

        List<String> errors = result.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        model.addAttribute("employeeWithRoles", result.getTarget());
        model.addAttribute("org.springframework.validation.BindingResult.employeeWithRoles", result);
        model.addAttribute("errorMessage",errors);

        return "editEmployee-form";
    }

    @ExceptionHandler(OrderValidationException.class)
    public String handleOrderValidation(OrderValidationException ex,
                                        RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/newOrderPage/" + ex.getOrderId();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleExceptionAndRedirect(IllegalArgumentException ex,
                                             RedirectAttributes redirectAttributes,
                                             HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("errorMessage",ex.getMessage());

        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }
        return "redirect:/main";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                     RedirectAttributes redirectAttributes,
                                     HttpServletRequest request) {

        String message = String.format("Wrong parameter format '%s'.", ex.getName());
        redirectAttributes.addFlashAttribute("errorMessage", message);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/main");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServlet(MissingServletRequestParameterException ex,
                                       RedirectAttributes redirectAttributes,
                                       HttpServletRequest request){
        String message = String.format("Cannot use action parameter '%s' is wrong or missing."
                , ex.getParameterName());
        redirectAttributes.addFlashAttribute("errorMessage", message);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/main");
    }

    @ExceptionHandler(PaginationException.class)
    public String handlePaginationException(PaginationException ex,
                                            RedirectAttributes redirectAttributes,
                                            HttpServletRequest request){
        String message = String.format("Wrong parameter format '%s'.", ex.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", message);

        return "redirect:" + ex.getFallbackUrl();
    }

    @ExceptionHandler(EmployeeAlreadyExists.class)
    public String handleDuplicateValue(EmployeeAlreadyExists ex,
                                       RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/registerForm";
    }
    @ExceptionHandler(InvalidOrderIdException.class)
    public String handleInvalidOrderId(InvalidOrderIdException ex,RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/getOrderPage";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleNotValidMethod(MethodArgumentNotValidException ex,
                                       RedirectAttributes redirectAttributes,HttpServletRequest request){
        String message = String.format("Validation failed '%s'"
                , ex.getParameter());
        redirectAttributes.addFlashAttribute("errorMessage", message);
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/main");
    }

    @ExceptionHandler(EmptyListException.class)
    public String handleEmptyList(EmptyListException ex,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletRequest request){
        redirectAttributes.addFlashAttribute("errorMessage",ex.getMessage());
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer: "/main");
    }

    @ExceptionHandler(QuantityOutOfBoundsException.class)
    public String handleQuantityOutOfBounds(QuantityOutOfBoundsException ex,
                                            RedirectAttributes redirectAttributes,
                                            HttpServletRequest request){

        redirectAttributes.addFlashAttribute("errorMessage",ex.getMessage());
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer: "/main");
    }


}