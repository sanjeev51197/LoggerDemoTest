package com.loggingex.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleEmployeeNotFound(ResourceNotFoundException ex)
    {
        logger.error("Exception Occured:{}",ex.getMessage());
        return ex.getMessage();
    }
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex)
    {
        logger.error("Unexpected error occured",ex);
         return "Internal Server Error";
    }
}
