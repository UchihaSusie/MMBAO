package com.rsa.webapi.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.activity.dao.AmActivityDao;
import com.rsa.webapi.activity.entity.AmActivity;
import com.rsa.webapi.activity.entity.AmActivityGoods;
import com.rsa.webapi.activity.service.AmActivityGoodsService;
import com.rsa.webapi.activity.service.AmActivityService;
import com.rsa.webapi.activity.vo.ActivityRequest;
import com.rsa.webapi.goods.entity.GgGoods;
import com.rsa.webapi.goods.service.GgGoodsService;
import com.rsa.webapi.user.entity.SysUser;
import com.rsa.webapi.user.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 活动管理-促销活动申请(AmActivity)表服务实现类
 *
 * @author makejava
 * @since 2024-06-17 10:53:42
 */
@Service("amActivityService")
public class AmActivityServiceImpl extends ServiceImpl<AmActivityDao, AmActivity> implements AmActivityService {
    @Resource
    AmActivityGoodsService activityGoodsService;
    @Resource
    GgGoodsService ggGoodsService;
    @Resource
    SysUserService userService;
    @Override
    public Page<AmActivity> listByPage(ActivityRequest request) {
        // 初始化分页参数，默认值
        Integer pageNum = Optional.ofNullable(request.getPageNum()).orElse(1);
        Integer pageSize = Optional.ofNullable(request.getPageSize()).orElse(9);
        Page<AmActivity> page = new Page<>(pageNum, pageSize);

        // 构建查询条件
        AmActivity activity = request.getActivity();
        QueryWrapper<AmActivity> queryWrapper = new QueryWrapper<>(activity);
        queryWrapper.eq("status",1);

        // 执行分页查询
        Page<AmActivity> activityPage = page(page, queryWrapper);
        enrichOrderWithGoodsInfo(activityPage.getRecords());
        return activityPage;
    }

    @Override
    public AmActivity getByIdWithGoods(Integer id) {
        AmActivity activity = getById(id);
        Integer activityId = activity.getId();

    // 查询活动关联的商品列表
        QueryWrapper<AmActivityGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_id", activityId);
        List<AmActivityGoods> amActivityGoodsList = activityGoodsService.list(queryWrapper);

    // 获取所有关联商品ID
        List<Integer> goodsIds = amActivityGoodsList.stream().map(AmActivityGoods::getGoodsId).collect(Collectors.toList());

    // 一次性查询所有关联的GgGoods信息，避免N+1查询问题
        List<GgGoods> ggGoodsList = ggGoodsService.listByIds(goodsIds);

    // 构建GgGoods的id到对象的映射，便于后续快速查找
        Map<Integer, GgGoods> ggGoodsMap = ggGoodsList.stream().collect(Collectors.toMap(GgGoods::getId, Function.identity()));

    // 设置AmActivityGoods的GgGoods相关信息
        amActivityGoodsList.forEach(amActivityGoods -> {
            GgGoods ggGoods = ggGoodsMap.get(amActivityGoods.getGoodsId());
            if (ggGoods != null) {
                amActivityGoods.setGname(ggGoods.getGname());
                amActivityGoods.setStock(ggGoods.getStock());
                amActivityGoods.setShopPrice(ggGoods.getShopPrice());
                amActivityGoods.setMarketPrice(ggGoods.getMarketPrice());
            }
        });
        activity.setActivityGoodsList(amActivityGoodsList);
        // 设置AmActivityGoods的审核人相关信息
        SysUser reviewer = userService.getById(activity.getReviewerId());
        activity.setReviewerName(reviewer.getUserName());
        return activity;
    }

    /**
     * 新增活动信息
     */
    @Override
    public boolean saveWithGoods(AmActivity amActivity) {
        amActivity.setStatus(1);
        amActivity.setReviewStatus(0);
        amActivity.setId(null);
        boolean save = save(amActivity);
        Integer activityId = amActivity.getId();
        List<AmActivityGoods> activityGoodsList = amActivity.getActivityGoodsList();
        activityGoodsList.stream().forEach(amActivityGoods -> {
            amActivityGoods.setId(null);
            amActivityGoods.setActivityId(activityId);
        });
        boolean b = activityGoodsService.saveBatch(activityGoodsList);
        if (save & b){
            return true;
        }
        return false;
    }

    /**
     * 遍历活动列表
     */
    private void enrichOrderWithGoodsInfo(List<AmActivity> amActivityList) {
        amActivityList.forEach(this::fetchAndSetGoodsInfo);
    }
    /**
     * 根据活动id查询并加入相关商品信息
     */
    private void fetchAndSetGoodsInfo(AmActivity activity) {
        List<AmActivityGoods> goodsList = activityGoodsService.getByAId(activity.getId());
        activity.setActivityGoodsList(goodsList);

    }

}

