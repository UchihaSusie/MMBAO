package com.rsa.webapi.goods.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品管理-商品类型表(GgGoodsType)表实体类
 *
 * @author makejava
 * @since 2024-06-14 15:12:28
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GgGoodsType extends Model<GgGoodsType> {

    private String id;

    private String typeName;

    private String descri;

    private Date createTime;

    private Date updateTime;

    private String pid;

}

