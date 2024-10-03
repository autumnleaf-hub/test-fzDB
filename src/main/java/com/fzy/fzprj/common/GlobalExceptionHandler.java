package com.fzy.fzprj.common;

import com.fzy.fzprj.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author fzy
 * @Description 全局异常处理器。原理：当Controller中的方法抛出异常时，会被这里捕获，然后返回给前端一个R对象。
 * @create 2023-02-09 21:51
 */
// 这个注解表示这是一个全局异常处理器，只要是Controller中的方法抛出异常，都会被这里捕获
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {

    // 指定捕获的异常类型
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> sQLIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e){
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        if(e.getMessage().contains("Duplicate entry")){
            log.error("用户名重复：{}", e.getMessage().split(" ")[2]);
            return R.error("用户名已存在");
        }

        return R.error("未知数据库错误");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> customExceptionHandler(CustomException e){
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return R.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R<String> exceptionHandler(Exception e){
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        return R.error("服务器内部错误");
    }
}
