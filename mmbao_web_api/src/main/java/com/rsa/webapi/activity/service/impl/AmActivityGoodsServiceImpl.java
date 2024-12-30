package com.rsa.webapi.activity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.activity.dao.AmActivityGoodsDao;
import com.rsa.webapi.activity.entity.AmActivityGoods;
import com.rsa.webapi.activity.service.AmActivityGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 活动管理-促销活动与商品关系表(AmActivityGoods)表服务实现类
 *
 * @author makejava
 * @since 2024-06-17 10:54:00
 */
@Service("amActivityGoodsService")
public class AmActivityGoodsServiceImpl extends ServiceImpl<AmActivityGoodsDao, AmActivityGoods> implements
    AmActivityGoodsService {
    @Resource
    AmActivityGoodsDao amActivityGoodsDao;
    @Override
    public List<AmActivityGoods> getByAId(Integer id) {
        List<AmActivityGoods> list = amActivityGoodsDao.selectByAId(id);
        return list;
    }
}

