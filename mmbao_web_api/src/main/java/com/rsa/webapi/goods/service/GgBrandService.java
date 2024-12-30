package com.rsa.webapi.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.goods.entity.GgBrand;
import com.rsa.webapi.goods.entity.PageBrand;

import java.util.List;

/**
 * 商品管理-品牌管理(GgBrand)表服务接口
 *
 * @author makejava
 * @since 2024-06-14 15:20:20
 */
public interface GgBrandService extends IService<GgBrand> {

    PageBrand seachByPageBrand(PageBrand pageBrand);

    List<GgBrand> getBrandByTypeId(Integer id);
}

