package com.rsa.webapi.activity.controller;



import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rsa.webapi.activity.entity.AmActivity;
import com.rsa.webapi.activity.service.AmActivityService;
import com.rsa.webapi.activity.vo.ActivityRequest;
import com.rsa.webapi.common.ReturnResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 活动管理-促销活动申请(AmActivity)表控制层
 *
 * @author makejava
 * @since 2024-06-17 10:53:42
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/activity")
public class AmActivityController  {
    /**
     * 服务对象
     */
    @Resource
    private AmActivityService amActivityService;

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/list")
    public ResponseEntity<ReturnResult> selectAll(@RequestBody ActivityRequest request) {
        Page<AmActivity> activityPage = amActivityService.listByPage(request);
        return ResponseEntity.ok(ReturnResult.ok(activityPage));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        AmActivity activity = amActivityService.getByIdWithGoods(id);
        return ResponseEntity.ok(ReturnResult.ok(activity));
    }

    /**
     * 新增数据
     *
     * @param amActivity 实体对象
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseEntity<ReturnResult> insert(@RequestBody AmActivity amActivity) {
        boolean save = amActivityService.saveWithGoods(amActivity);
        return ResponseEntity.ok(ReturnResult.ok(save));
    }

    /**
     * 修改数据
     *
     * @param amActivity 实体对象
     * @return 修改结果
     */
    @PutMapping("/edit")
    public ResponseEntity<ReturnResult> update(@RequestBody AmActivity amActivity) {
        boolean b = amActivityService.updateById(amActivity);
        return ResponseEntity.ok(ReturnResult.ok(b));
    }

    /**
     * 根据主键删除数据
     * @return 删除结果
     */
    @PostMapping("/del/{id}")
    public ResponseEntity<ReturnResult> delete( @PathVariable Integer id) {
        UpdateWrapper<AmActivity> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("status",0);
        amActivityService.update(wrapper);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

