package com.example.springboot.common;


import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String msg;
    private T data;
    private Result(){}

    private Result(CodeMsg codeMsg){
        if(codeMsg != null){
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }

    private Result(T data,CodeMsg codeMsg){
        if(codeMsg != null){
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
        this.data = data;
    }

//    定义统一的成功返回函数
public static <T>Result<T> success(T data){
    return new Result<T>(data,CodeMsg.SUCCESS);
}
//    定义统一的失败返回函数
public static <T>Result<T> error(CodeMsg codeMsg){
    return new Result<T>(codeMsg);
}





}
