package com.rsa.webapi.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.goods.dao.GgGoodsDao;
import com.rsa.webapi.goods.entity.GgGoods;
import com.rsa.webapi.goods.entity.PageGoods;
import com.rsa.webapi.goods.service.GgGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品管理-商品管理(GgGoods)表服务实现类
 *
 * @author makejava
 * @since 2024-06-13 15:23:45
 */
@Service("ggGoodsService")
public class GgGoodsServiceImpl extends  ServiceImpl <GgGoodsDao, GgGoods> implements
    GgGoodsService {

    @Resource
    GgGoodsDao goodsDao;
    @Override
    public PageGoods seachByPageGood(PageGoods pageGoods) {
        //goods 数据
        GgGoods ggGoods = pageGoods.getGgGoods();
        QueryWrapper<GgGoods> queryWrapper = new QueryWrapper<>();
            if(ggGoods.getGname() != null && (!"".equals(ggGoods.getGname()))){
                queryWrapper.like("gname",ggGoods.getGname());
            }
            if(ggGoods.getGcolor() != null && (!"".equals(ggGoods.getGcolor()))){
                queryWrapper.eq("gcolor",ggGoods.getGcolor());
            }
            if(ggGoods.getGtype() != null && (!"".equals(ggGoods.getGtype()))){
                queryWrapper.like("gtype",ggGoods.getGtype());
            }
            if(ggGoods.getGbrand() != null && (!"".equals(ggGoods.getGbrand()))){
                queryWrapper.eq("gbrand",ggGoods.getGbrand());
            }
            if(ggGoods.getGmodel() != null && (!"".equals(ggGoods.getGmodel()))){
                queryWrapper.like("gmodel",ggGoods.getGmodel());
            }

        //page 数据
        queryWrapper.orderByAsc("id");
        Page<GgGoods> page = new Page<>(pageGoods.getPageNum(), pageGoods.getPageSize());
        IPage<GgGoods> iPage = goodsDao.selectPage(page,queryWrapper);
        pageGoods.setPageTotal((int) iPage.getTotal());
        pageGoods.setGoodsList(iPage.getRecords());
        return pageGoods;
    }
}

