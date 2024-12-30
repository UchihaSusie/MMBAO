package com.rsa.webapi.omorder.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rsa.webapi.omorder.entity.OmOrderGoods;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单管理-b2c订单与商品关系表
(OmOrderGoods)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-13 15:18:13
 */
public interface OmOrderGoodsDao extends BaseMapper<OmOrderGoods> {
    @Select("SELECT * FROM om_order_goods WHERE order_code = #{orderCode}")
    List<OmOrderGoods> selectByOrderCode(Integer orderCode);
}

