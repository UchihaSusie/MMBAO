package com.rsa.webapi.common.exception;

import com.rsa.webapi.common.ExceptionEnum;
import lombok.Getter;

/**
 * @author : gfs
 * @className: JavasmException
 * @description:
 * @date: 2024/5/27 11:50
 * @version: 0.1
 * @since: jdk11
 */
@Getter
public class JavasmException extends RuntimeException{
    private ExceptionEnum exceptionEnum;
    public JavasmException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.exceptionEnum = exceptionEnum;
    }
}
