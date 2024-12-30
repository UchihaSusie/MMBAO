package com.rsa.webapi.omorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.omorder.entity.OmOrderGoods;

import java.util.List;

/**
 * 订单管理-b2c订单与商品关系表
(OmOrderGoods)表服务接口
 *
 * @author makejava
 * @since 2024-06-13 15:18:13
 */
public interface OmOrderGoodsService extends IService<OmOrderGoods> {


    List<OmOrderGoods> getByOrderCode(Integer orderCode);
}

