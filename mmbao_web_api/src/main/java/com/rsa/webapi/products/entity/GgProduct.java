package com.rsa.webapi.products.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品管理-产品查询
(GgProduct)表实体类
 *
 * @author makejava
 * @since 2024-06-13 14:56:16
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GgProduct extends Model<GgProduct> {
    //序号
    @TableId(type = IdType.AUTO)
    private Integer id;
    //存货档案编码
    private String coding;
    //基础库-分类
    private String classify;
    //基础库-品牌
    private String brand;
    //基础库-型号
    private String model;
    //基础库-业务模式
    private String business;
    //基础库-颜色 (0=白色,1=黑色,2=绚丽紫,3珍珠白,4=乌漆嘛黑)
    private Integer color;
    //基础库-采购模式
    private String purchasingPattern;
    //基础库-铺货
    private String distribution;
    //基础库-备机
    private String standby;
    
    private Date createTime;
    
    private Date updateTime;

}

