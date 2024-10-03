package com.fzy.fzprj.exceptions;

/**
 * @author fzy
 * @Description 自定义运行时异常，这个异常抛出后会被全局异常捕获器捕获，并将其中的错误信息直接返回到前端
 * @create 2023-02-14 20:55
 */


public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }

}
