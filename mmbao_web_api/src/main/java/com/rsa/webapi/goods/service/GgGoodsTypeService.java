package com.rsa.webapi.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.goods.entity.GgGoodsType;
import com.rsa.webapi.goods.entity.PageType;

/**
 * 商品管理-商品类型表(GgGoodsType)表服务接口
 *
 * @author makejava
 * @since 2024-06-14 15:12:28
 */
public interface GgGoodsTypeService extends IService<GgGoodsType> {

    PageType seachByPageType(PageType pageType);
}

