package com.rsa.webapi.common.exception;

import com.rsa.webapi.common.ExceptionEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author : gfs
 * @className: ExceptionAdpator
 * @description:
 * @date: 2024/5/27 11:50
 * @version: 0.1
 * @since: jdk11
 */
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(JavasmException.class)
    public ResponseEntity f1(JavasmException javasmException){
        ExceptionEnum exceptionEnum = javasmException.getExceptionEnum();

        return ResponseEntity.status(exceptionEnum.getCode()).body(exceptionEnum);
    }
}
