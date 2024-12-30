package com.rsa.webapi.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : gfs
 * @className: ReturnResult
 * @description:
 * @date: 2024/5/22 15:01
 * @version: 0.1
 * @since: jdk11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnResult {
    private Integer code;
    private String msg;
    private Object data;

    public ReturnResult(ExceptionEnum exceptionEnum){
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    public ReturnResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ReturnResult ok(){
        return ok(null);
    }
    public static ReturnResult ok(Object data){
        return new ReturnResult(200,"success",data);
    }
}
