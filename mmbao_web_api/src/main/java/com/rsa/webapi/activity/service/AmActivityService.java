package com.rsa.webapi.activity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.activity.entity.AmActivity;
import com.rsa.webapi.activity.vo.ActivityRequest;

/**
 * 活动管理-促销活动申请(AmActivity)表服务接口
 *
 * @author makejava
 * @since 2024-06-17 10:53:42
 */
public interface AmActivityService extends IService<AmActivity> {
    /**
     * 分页条件查询
     * @param request ActivityRequest request
     * @return 分页菜单列表
     */
    Page<AmActivity> listByPage(ActivityRequest request);
    /**
     * 根据主键查询单条数据
     * @param id 主键
     * @return 单条数据包括商品列表
     */
    AmActivity getByIdWithGoods(Integer id);

    boolean saveWithGoods(AmActivity amActivity);
}

