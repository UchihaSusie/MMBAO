package com.rsa.webapi.activity.vo;

import com.rsa.webapi.activity.entity.AmActivity;
import lombok.Data;

/**
 * @author : cgq
 * @className: ActivityRequest
 * @description:
 * @date: 2024/6/17 11:10
 * @version: 0.1
 * @since: jdk11
 */
@Data
public class ActivityRequest {
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 查询条件
     */
    private AmActivity activity;
}
