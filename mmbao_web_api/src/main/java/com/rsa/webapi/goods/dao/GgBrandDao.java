package com.rsa.webapi.goods.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rsa.webapi.goods.entity.GgBrand;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商品管理-品牌管理(GgBrand)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-14 15:20:20
 */
public interface GgBrandDao extends BaseMapper<GgBrand> {

    @Select("SELECT b.* from gg_brand b, gg_goodstype_brand r where r.brand_id = b.id AND r.gtype_id = #{id}")
    List<GgBrand> getBrandByTypeId(Integer id);
}

