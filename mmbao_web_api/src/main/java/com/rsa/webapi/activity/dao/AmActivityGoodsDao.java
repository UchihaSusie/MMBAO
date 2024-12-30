package com.rsa.webapi.activity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rsa.webapi.activity.entity.AmActivityGoods;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 活动管理-促销活动与商品关系表(AmActivityGoods)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-17 10:54:00
 */
public interface AmActivityGoodsDao extends BaseMapper<AmActivityGoods> {
    @Select("SELECT aag.id,aag.activity_id, aag.goods_id, aag.promotion_amount, aag.discount_type, aag.percentage, aag.mall_promotion_price, gg.gcode, gg.gname, gg.stock,gg.shop_price, gg.market_price  " +
            "FROM am_activity_goods aag, gg_goods gg WHERE aag.goods_id = gg.id and activity_id = #{id}")
    List<AmActivityGoods> selectByAId(Integer id);
}

