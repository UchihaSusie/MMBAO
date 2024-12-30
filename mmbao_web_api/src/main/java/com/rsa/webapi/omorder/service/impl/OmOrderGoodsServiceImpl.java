package com.rsa.webapi.omorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.omorder.dao.OmOrderGoodsDao;
import com.rsa.webapi.omorder.entity.OmOrderGoods;
import com.rsa.webapi.omorder.service.OmOrderGoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单管理-b2c订单与商品关系表
(OmOrderGoods)表服务实现类
 *
 * @author makejava
 * @since 2024-06-13 15:18:13
 */
@Service("omOrderGoodsService")
public class OmOrderGoodsServiceImpl extends ServiceImpl<OmOrderGoodsDao, OmOrderGoods> implements OmOrderGoodsService {
    @Resource
    OmOrderGoodsDao omOrderGoodsDao;
    @Override
    public List<OmOrderGoods> getByOrderCode(Integer orderCode) {
        List<OmOrderGoods> list = omOrderGoodsDao.selectByOrderCode(orderCode);
        return list;
    }
}

