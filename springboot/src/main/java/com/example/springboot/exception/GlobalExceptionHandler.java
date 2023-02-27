package com.example.springboot.exception;

import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CodeMsg.class)
    @ResponseBody
    public Result handle(CodeMsg SE){
        return Result.error(SE);
    }

}
