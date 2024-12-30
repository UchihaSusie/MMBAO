package com.rsa.webapi.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : gfs
 * @className: RandomUtil
 * @description:
 * @date: 2024/5/21 10:45
 * @version: 0.1
 * @since: jdk11
 */
public class RandomUtil {
    private RandomUtil(){}

    private final static String pool = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public static String getString(int length){
        if (length < 0 ){
            throw new IllegalArgumentException("数据异常");
        }
        StringBuilder result = new StringBuilder();
        for (int i=0;i<length;i++){
            int index = ThreadLocalRandom.current().nextInt(pool.length());
            char c = pool.charAt(index);
            result.append(c);
        }
        return result.toString();
    }

    //数字获取
    public static String getCode(int length){
        if (length < 0 ){
            throw new IllegalArgumentException("数据异常");
        }
        StringBuilder result = new StringBuilder();
        for (int i=0;i<length;i++){
            int num = ThreadLocalRandom.current().nextInt(0,10);
            result.append(num);
        }
        return result.toString();
    }
    public static String getFileName(String fileName){
        //fileName  abc.jpg
        // 使用lastIndexOf()找到最后一个"."的位置
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1){
            new RuntimeException("文件名格式错误");
        }
        String fileType = fileName.substring(dotIndex + 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time = simpleDateFormat.format(new Date());
        String name = time+getString(5)+"."+fileType;
        return name;
    }

    public static void main(String[] args) {
        String fileName = getCode(6);
        System.out.println(fileName);
    }
}
