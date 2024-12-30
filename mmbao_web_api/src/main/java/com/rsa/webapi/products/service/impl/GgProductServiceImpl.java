package com.rsa.webapi.products.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.products.dao.GgProductDao;
import com.rsa.webapi.products.entity.GgPageProduct;
import com.rsa.webapi.products.entity.GgProduct;
import com.rsa.webapi.products.service.GgProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品管理-产品查询
(GgProduct)表服务实现类
 *
 * @author makejava
 * @since 2024-06-13 14:56:16
 */
@Service("ggProductService")
public class GgProductServiceImpl extends  ServiceImpl <GgProductDao, GgProduct> implements GgProductService{
    @Resource
    GgProductDao productDao;
    @Override
    public GgPageProduct seachByProduct(GgPageProduct ggPageProduct) {
        //product 数据
        GgProduct ggProduct = ggPageProduct.getGgProduct();
        QueryWrapper<GgProduct>queryWrapper = new QueryWrapper<>();
        if(ggProduct.getCoding() != null && (!"".equals(ggProduct.getCoding()))){
            queryWrapper.like("coding",ggProduct.getCoding());
        }
        if(ggProduct.getColor() != null && (!"".equals(ggProduct.getColor()))){
            queryWrapper.eq("color",ggProduct.getColor());
        }
        if(ggProduct.getClassify() != null && (!"".equals(ggProduct.getClassify()))){
            queryWrapper.eq("classify",ggProduct.getClassify());
        }
        if(ggProduct.getBrand() != null && (!"".equals(ggProduct.getBrand()))){
            queryWrapper.eq("brand",ggProduct.getBrand());
        }
        if(ggProduct.getModel() != null && (!"".equals(ggProduct.getModel()))){
            queryWrapper.like("model",ggProduct.getModel());
        }

        //page 数据
        queryWrapper.orderByAsc("id");
        Page<GgProduct> page = new Page<>(ggPageProduct.getPageNum(),ggPageProduct.getPageSize());
        IPage<GgProduct> iPage = productDao.selectPage(page,queryWrapper);
        ggPageProduct.setPageTotal((int) iPage.getTotal());
        ggPageProduct.setProList(iPage.getRecords());
        return ggPageProduct;
    }

    @Override
    public GgProduct getByCode(String code) {
        QueryWrapper<GgProduct>queryWrapper = new QueryWrapper<>();
        if(code!= null && (!"".equals(code))){
            queryWrapper.like("coding",code);
        }
        return productDao.selectOne(queryWrapper);
    }
}

