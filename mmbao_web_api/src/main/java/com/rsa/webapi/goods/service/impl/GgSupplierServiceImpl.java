package com.rsa.webapi.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.goods.dao.GgSupplierDao;
import com.rsa.webapi.goods.entity.*;
import com.rsa.webapi.goods.entity.GgGoods;
import com.rsa.webapi.goods.entity.GgSupplier;
import com.rsa.webapi.goods.entity.PageSupplier;
import com.rsa.webapi.goods.service.GgSupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理-供应商(GgSupplier)表服务实现类
 *
 * @author makejava
 * @since 2024-06-14 23:07:25
 */
@Service("ggSupplierService")
public class GgSupplierServiceImpl extends  ServiceImpl <GgSupplierDao, GgSupplier> implements
    GgSupplierService {

    @Resource
    GgSupplierDao supplierDao;
    @Override
    public PageSupplier seachByPageSupplier(PageSupplier pageSupplier) {
        //suppliers 数据
        GgSupplier ggSupplier = pageSupplier.getGgSupplier();
        QueryWrapper<GgSupplier> queryWrapper = new QueryWrapper<>();
        if(ggSupplier.getSupplierName() != null && (!"".equals(ggSupplier.getSupplierName()))){
            queryWrapper.like("supplier_name",ggSupplier.getSupplierName());
        }
        if(ggSupplier.getContacts() != null && (!"".equals(ggSupplier.getContacts()))){
            queryWrapper.like("contacts",ggSupplier.getContacts());
        }
        if(ggSupplier.getContactPhone() != null && (!"".equals(ggSupplier.getContactPhone()))){
            queryWrapper.like("contact_phone",ggSupplier.getContactPhone());
        }
        if(ggSupplier.getContactEmail() != null && (!"".equals(ggSupplier.getContactEmail()))){
            queryWrapper.like("contact_email",ggSupplier.getContactEmail());
        }
        if(ggSupplier.getContactAddress() != null && (!"".equals(ggSupplier.getContactAddress()))){
            queryWrapper.like("contact_address",ggSupplier.getContactAddress());
        }
        if(ggSupplier.getAccountNo() != null && (!"".equals(ggSupplier.getAccountNo()))){
            queryWrapper.like("account_no",ggSupplier.getAccountNo());
        }

        //page 数据
        queryWrapper.orderByAsc("id");
        Page<GgSupplier> page = new Page<>(pageSupplier.getPageNum(), pageSupplier.getPageSize());
        IPage<GgSupplier> iPage = supplierDao.selectPage(page,queryWrapper);
        pageSupplier.setPageTotal((int) iPage.getTotal());
        pageSupplier.setSupList(iPage.getRecords());
        return pageSupplier;
    }

    @Override
    public List<GgGoods> goodBySupplyId(Integer id) {
        return supplierDao.goodBySupplyId(id);
    }

    @Override
    public void deleteGoodId(Integer gid, Integer sid) {
         supplierDao.deleteGoodId(gid, sid);
    }

    @Override
    public void addGoodId(Integer gid, Integer sid) {
         supplierDao.addGoodId(gid, sid);
    }
}

