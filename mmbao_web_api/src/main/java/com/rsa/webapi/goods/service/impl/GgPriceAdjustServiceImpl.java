package com.rsa.webapi.goods.service.impl;

import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.goods.dao.GgPriceAdjustDao;
import com.rsa.webapi.goods.entity.GgPriceAdjust;
import com.rsa.webapi.goods.entity.PageAdjust;
import com.rsa.webapi.goods.service.GgPriceAdjustService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 商品管理-价格调整(GgPriceAdjust)表服务实现类
 *
 * @author makejava
 * @since 2024-06-17 22:10:56
 */
@Service("ggPriceAdjustService")
public class GgPriceAdjustServiceImpl extends  ServiceImpl <GgPriceAdjustDao, GgPriceAdjust> implements
    GgPriceAdjustService {

    @Resource
    GgPriceAdjustDao priceAdjustDao;

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public PageAdjust seachByPageAdjust(PageAdjust pageAdjust) {
        //priceAdjust 数据
        GgPriceAdjust ggPriceAdjust = pageAdjust.getGgAdjust();
        QueryWrapper<GgPriceAdjust> queryWrapper = new QueryWrapper<>();
        if(ggPriceAdjust.getGoodsName() != null && (!"".equals(ggPriceAdjust.getGoodsName()))){
            queryWrapper.like("goods_name",ggPriceAdjust.getGoodsName());
        }
        if(ggPriceAdjust.getBeginTime() != null && (!"".equals(ggPriceAdjust.getBeginTime()))){
            String formattedDate = formatter.format(ggPriceAdjust.getBeginTime());
            queryWrapper.like("begin_time",formattedDate);
        }
        if(ggPriceAdjust.getApplyUser() != null && (!"".equals(ggPriceAdjust.getApplyUser()))){
            queryWrapper.eq("apply_user",ggPriceAdjust.getApplyUser());
        }
        if(ggPriceAdjust.getEndTime() != null && (!"".equals(ggPriceAdjust.getEndTime()))){
            String formattedDate = formatter.format(ggPriceAdjust.getEndTime());
            queryWrapper.like("end_time",formattedDate);
        }
        if(ggPriceAdjust.getProcessStatus() != null && (!"".equals(ggPriceAdjust.getProcessStatus()))){
            queryWrapper.eq("process_status",ggPriceAdjust.getProcessStatus());
        }
        if(ggPriceAdjust.getAdjustMarketPrice() != null && (!"".equals(ggPriceAdjust.getAdjustMarketPrice()))){
            queryWrapper.eq("adjust_market_price",ggPriceAdjust.getAdjustMarketPrice());
        }


        //page 数据
        queryWrapper.orderByAsc("id");
        Page<GgPriceAdjust> page = new Page<>(pageAdjust.getPageNum(), pageAdjust.getPageSize());
        IPage<GgPriceAdjust> iPage = priceAdjustDao.selectPage(page,queryWrapper);
        pageAdjust.setPageTotal((int) iPage.getTotal());
        pageAdjust.setAdjustList(iPage.getRecords());
        return pageAdjust;
    }
}

