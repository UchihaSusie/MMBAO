package com.rsa.webapi.common;

/**
 * @author : gfs
 * @className: ParameterUtils
 * @description:
 * @date: 2024/5/27 11:44
 * @version: 0.1
 * @since: jdk11
 */
public class ParameterUtils {


    public static boolean checkParam(Object... args) {
        if (args == null || args.length == 0) {
            return false;
        }
        for (Object obj : args){
            if (obj == null){
                return false;
            }
            if (obj instanceof String && "".equals(obj.toString())){
                return  false;
            }
        }
        return true;
    }
}
