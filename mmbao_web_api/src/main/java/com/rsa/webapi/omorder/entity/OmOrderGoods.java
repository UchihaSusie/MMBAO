package com.rsa.webapi.omorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

/**
 * 订单管理-b2c订单与商品关系表
(OmOrderGoods)表实体类
 *
 * @author makejava
 * @since 2024-06-13 15:18:13
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmOrderGoods extends Model<OmOrderGoods> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String goodsCode;
    
    private String goodsName;
    
    private String goodsPrice;
    
    private Integer orderCode;
    
    private Integer count;
    
    private String price;




}

