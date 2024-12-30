package com.rsa.webapi.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.goods.dao.GgBrandDao;
import com.rsa.webapi.goods.entity.GgBrand;
import com.rsa.webapi.goods.entity.PageBrand;
import com.rsa.webapi.goods.service.GgBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理-品牌管理(GgBrand)表服务实现类
 *
 * @author makejava
 * @since 2024-06-14 15:20:20
 */
@Service("ggBrandService")
public class GgBrandServiceImpl extends  ServiceImpl <GgBrandDao, GgBrand> implements
    GgBrandService {

    @Resource
    GgBrandDao brandDao;
    @Override
    public PageBrand seachByPageBrand(PageBrand pageBrand) {
        //brands 数据
        GgBrand ggBrand = pageBrand.getGgBrand();
        QueryWrapper<GgBrand> queryWrapper = new QueryWrapper<>();
        if(ggBrand.getBrandName() != null && (!"".equals(ggBrand.getBrandName()))){
            queryWrapper.like("brand_name",ggBrand.getBrandName());
        }
        //page 数据
        queryWrapper.orderByAsc("id");
        Page<GgBrand> page = new Page<>(pageBrand.getPageNum(), pageBrand.getPageSize());
        IPage<GgBrand> iPage = brandDao.selectPage(page,queryWrapper);
        pageBrand.setPageTotal((int) iPage.getTotal());
        pageBrand.setBrandList(iPage.getRecords());
        return pageBrand;
    }

    @Override
    public List<GgBrand> getBrandByTypeId(Integer id) {
        return brandDao.getBrandByTypeId(id);
    }
}

