package com.rsa.webapi.activity.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.List;

import lombok.*;

/**
 * 活动管理-促销活动申请(AmActivity)表实体类
 *
 * @author makejava
 * @since 2024-06-17 10:53:42
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmActivity extends Model<AmActivity> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String activityName;
    
    private LocalDate activityBeginTime;
    
    private LocalDate activityEndTime;
    
    private Integer activityType;
    
    private LocalDate priceBeginTime;
    
    private LocalDate priceEndTime;
    
    private String activityReason;
    
    private String remark;

    private Integer reviewStatus;

    private String creatorId;

    private LocalDateTime createTime;

    private Integer reviewerId;

    private LocalDateTime reviewerTime;

    private Integer status;
    @TableField(exist = false)
    private String reviewerName;
    /**
     * 该活动的商品列表
     */
    @TableField(exist = false)
    private List<AmActivityGoods> activityGoodsList;

}

