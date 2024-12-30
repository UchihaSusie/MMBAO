package com.rsa.webapi.products.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.products.entity.GgPageProduct;
import com.rsa.webapi.products.entity.GgProduct;

/**
 * 商品管理-产品查询
(GgProduct)表服务接口
 *
 * @author makejava
 * @since 2024-06-13 14:56:16
 */
public interface GgProductService extends IService<GgProduct> {
    GgPageProduct seachByProduct(GgPageProduct ggPageProduct);

    GgProduct getByCode(String code);
}

