package com.rsa.webapi.goods.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品管理-价格调整(GgPriceAdjust)表实体类
 *
 * @author makejava
 * @since 2024-06-17 22:10:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GgPriceAdjust extends Model<GgPriceAdjust> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String applyUser;
    
    private Integer goodsId;

    private String goodsName;
    
    private String adjustWay;
    
    private Double percent;
    
    private Double adjustMarketPrice;
    
    private Double adjustShoppingPrice;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private Date endTime;
    //调价原因
    private String reason;
    //备注
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date updateTime;
    
    private String processStatus;

    private String checkAdvice;

}

