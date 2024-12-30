package com.rsa.webapi.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.goods.dao.GgGoodstypeBrandDao;
import com.rsa.webapi.goods.entity.GgGoodstypeBrand;
import com.rsa.webapi.goods.service.GgGoodstypeBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 商品管理-商品类型与品牌关系表(GgGoodstypeBrand)表服务实现类
 *
 * @author makejava
 * @since 2024-06-17 10:08:05
 */
@Service("ggGoodstypeBrandService")
public class GgGoodstypeBrandServiceImpl extends  ServiceImpl <GgGoodstypeBrandDao, GgGoodstypeBrand> implements
    GgGoodstypeBrandService {

    @Resource
    GgGoodstypeBrandDao goodstypeBrandDao;
    @Override
    public void saveBrandsList(GgGoodstypeBrand ggGoodstypeBrand) {
        QueryWrapper<GgGoodstypeBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gtype_id", ggGoodstypeBrand.getGtypeId());
        //remove old
        goodstypeBrandDao.delete(queryWrapper);
        //add all brand
        List<GgGoodstypeBrand> newBrandList = new ArrayList<>();
        ggGoodstypeBrand.getBrandIdList().forEach(brandId ->{
            GgGoodstypeBrand obj = new GgGoodstypeBrand();
            obj.setGtypeId(ggGoodstypeBrand.getGtypeId());
            obj.setBrandId(brandId);
            newBrandList.add(obj);
        });
      saveBatch(newBrandList);
    }
}

