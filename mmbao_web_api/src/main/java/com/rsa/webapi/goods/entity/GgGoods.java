package com.rsa.webapi.goods.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品管理-商品管理(GgGoods)表实体类
 *
 * @author makejava
 * @since 2024-06-13 15:23:45
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GgGoods extends Model<GgGoods> {

    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String gcode;
    
    private String gname;
    
    private String gtype;
    
    private Double purchasePrice;

    private Double marketPrice;

    private Double shopPrice;
    
    private Integer stock;

    private Integer minStock;

    private String remark;
    
    private Integer productId;
    
    private Date createTime;
    
    private Date updateTime;
    
    private String gbrand;
    
    private String gmodel;
    
    private String gcolor;

}

