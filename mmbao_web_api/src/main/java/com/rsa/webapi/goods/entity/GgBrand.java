package com.rsa.webapi.goods.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品管理-品牌管理(GgBrand)表实体类
 *
 * @author makejava
 * @since 2024-06-14 15:20:20
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GgBrand extends Model<GgBrand> {
    
    private Integer id;
    //品牌名称
    private String brandName;
    //品牌网址
    private String brandUrl;
    //品牌描述
    private String brandDescribe;

}

