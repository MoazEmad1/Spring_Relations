package com.example.relations.controller;

import com.example.relations.exception.UpdateFailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UpdateFailException.class)
    public ModelAndView handleUpdateFailedException(UpdateFailException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.setViewName("error-page");
        return modelAndView;
    }
}
