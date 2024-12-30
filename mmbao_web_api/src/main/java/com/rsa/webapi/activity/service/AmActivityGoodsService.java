package com.rsa.webapi.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.activity.entity.AmActivityGoods;

import java.util.List;

/**
 * 活动管理-促销活动与商品关系表(AmActivityGoods)表服务接口
 *
 * @author makejava
 * @since 2024-06-17 10:54:00
 */
public interface AmActivityGoodsService extends IService<AmActivityGoods> {

    List<AmActivityGoods> getByAId(Integer id);
}

