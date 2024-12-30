package com.rsa.webapi.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.goods.entity.GgPriceAdjust;
import com.rsa.webapi.goods.entity.PageAdjust;

/**
 * 商品管理-价格调整(GgPriceAdjust)表服务接口
 *
 * @author makejava
 * @since 2024-06-17 22:10:56
 */
public interface GgPriceAdjustService extends IService<GgPriceAdjust> {

    PageAdjust seachByPageAdjust(PageAdjust pageAdjust);
}

