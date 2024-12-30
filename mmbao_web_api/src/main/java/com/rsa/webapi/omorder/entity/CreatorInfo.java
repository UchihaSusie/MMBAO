package com.rsa.webapi.omorder.entity;

import lombok.Data;

/**
 * @author : cgq
 * @className: CreatorInfo
 * @description: 出库单创建人信息
 * @date: 2024/6/16 21:23
 * @version: 0.1
 * @since: jdk11
 */
@Data
public class CreatorInfo {
    private String creatorName;
    private String createTime;
}
