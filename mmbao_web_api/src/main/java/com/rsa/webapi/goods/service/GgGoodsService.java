package com.rsa.webapi.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.goods.entity.GgGoods;
import com.rsa.webapi.goods.entity.PageGoods;

/**
 * 商品管理-商品管理(GgGoods)表服务接口
 *
 * @author makejava
 * @since 2024-06-13 15:23:45
 */
public interface GgGoodsService extends IService<GgGoods> {

    PageGoods seachByPageGood(PageGoods pageGoods);
}

