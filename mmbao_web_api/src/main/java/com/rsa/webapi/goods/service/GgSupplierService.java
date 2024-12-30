package com.rsa.webapi.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.goods.entity.GgGoods;
import com.rsa.webapi.goods.entity.GgSupplier;
import com.rsa.webapi.goods.entity.PageSupplier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品管理-供应商(GgSupplier)表服务接口
 *
 * @author makejava
 * @since 2024-06-14 23:07:25
 */

@Transactional
public interface GgSupplierService extends IService<GgSupplier> {

    PageSupplier seachByPageSupplier(PageSupplier pageSupplier);

    List<GgGoods> goodBySupplyId(Integer id);

    void deleteGoodId(Integer gid, Integer sid);

    void addGoodId(Integer gid, Integer sid);
}

