package com.rsa.webapi.goods.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品管理-商品类型与品牌关系表(GgGoodstypeBrand)表实体类
 *
 * @author makejava
 * @since 2024-06-17 10:08:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GgGoodstypeBrand extends Model<GgGoodstypeBrand> {
    
    private Integer gtypeId;
    
    private Integer brandId;

    @TableField(exist = false)
    private List<Integer> brandIdList;

}

