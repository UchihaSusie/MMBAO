package com.rsa.webapi.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.goods.entity.GgGoodstypeBrand;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品管理-商品类型与品牌关系表(GgGoodstypeBrand)表服务接口
 *
 * @author makejava
 * @since 2024-06-17 10:08:05
 */
@Transactional
public interface GgGoodstypeBrandService extends IService<GgGoodstypeBrand> {

    void saveBrandsList(GgGoodstypeBrand ggGoodstypeBrand);
}

