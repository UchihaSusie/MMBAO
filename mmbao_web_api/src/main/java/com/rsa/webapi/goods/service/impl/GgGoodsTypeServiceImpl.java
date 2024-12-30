package com.rsa.webapi.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.goods.dao.GgGoodsTypeDao;
import com.rsa.webapi.goods.entity.GgGoodsType;
import com.rsa.webapi.goods.entity.PageType;
import com.rsa.webapi.goods.service.GgGoodsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商品管理-商品类型表(GgGoodsType)表服务实现类
 *
 * @author makejava
 * @since 2024-06-14 15:12:28
 */
@Service("ggGoodsTypeService")
public class GgGoodsTypeServiceImpl extends  ServiceImpl <GgGoodsTypeDao, GgGoodsType> implements
    GgGoodsTypeService {

    @Resource
    GgGoodsTypeDao typeDao;
    @Override
    public PageType seachByPageType(PageType pageType) {
        //types 数据
        GgGoodsType ggGoodsType = pageType.getGgTypes();
        QueryWrapper<GgGoodsType> queryWrapper = new QueryWrapper<>();
        if(ggGoodsType.getTypeName() != null && (!"".equals(ggGoodsType.getTypeName()))){
            queryWrapper.like("type_name",ggGoodsType.getTypeName());
        }
        //page 数据
        queryWrapper.orderByAsc("id");
        Page<GgGoodsType> page = new Page<>(pageType.getPageNum(), pageType.getPageSize());
        IPage<GgGoodsType> iPage = typeDao.selectPage(page,queryWrapper);
        pageType.setPageTotal((int) iPage.getTotal());
        pageType.setTypeList(iPage.getRecords());
        return pageType;
    }
}

