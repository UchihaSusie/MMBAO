package com.rsa.webapi.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.*;

/**
 * 活动管理-促销活动与商品关系表(AmActivityGoods)表实体类
 *
 * @author makejava
 * @since 2024-06-17 10:54:00
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmActivityGoods extends Model<AmActivityGoods> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer activityId;
    
    private Integer goodsId;
    
    private Integer promotionAmount;
    
    private String discountType;
    
    private String percentage;
    
    private Double mallPromotionPrice;
    @TableField(exist = false)
    private String gname;
    @TableField(exist = false)
    private Integer stock;
    @TableField(exist = false)
    private Double shopPrice;
    @TableField(exist = false)
    private Double marketPrice;

}

