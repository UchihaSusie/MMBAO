package com.rsa.webapi.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : gfs
 * @className: ExceptionEnum
 * @description:
 * @date: 2024/5/22 15:03
 * @version: 0.1
 * @since: jdk11
 */
@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExceptionEnum {

    PARAMETER_NULL(1001,"参数为空"),
    PAGE_ERROR(1002,"分页异常"),
    SYSTEMERROR(1003,"系统异常"),
    //异常登录
    LOGINERROR(3001,"登录失败"),
    TOKENERROR(3002,"token失效"),
    SIGNERROR(3003,"签名异常"),

    LOGINCODEERROR(3004,"验证码错误"),

    GOODS_LESS(5001,"商品库存不足"),
    TASK_NOT_FOUND(6001,"任务不存在"),
    FILE_ADDRESS_ERROR(7001,"文件地址错误");



    private Integer code;
    private String msg;



}
