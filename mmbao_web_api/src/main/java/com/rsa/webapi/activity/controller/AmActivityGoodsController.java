package com.rsa.webapi.activity.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rsa.webapi.activity.entity.AmActivityGoods;
import com.rsa.webapi.activity.service.AmActivityGoodsService;
import com.rsa.webapi.common.ReturnResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 活动管理-促销活动与商品关系表(AmActivityGoods)表控制层
 *
 * @author makejava
 * @since 2024-06-17 10:54:00
 */
@RestController
@RequestMapping("amActivityGoods")
public class AmActivityGoodsController  {
    /**
     * 服务对象
     */
    @Resource
    private AmActivityGoodsService amActivityGoodsService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param amActivityGoods 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity<ReturnResult> selectAll(Page<AmActivityGoods> page, AmActivityGoods amActivityGoods) {
        amActivityGoodsService.page(page, new QueryWrapper<>(amActivityGoods));
        return ResponseEntity.ok(ReturnResult.ok());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Serializable id) {
        amActivityGoodsService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok()); 
    }

    /**
     * 新增数据
     *
     * @param amActivityGoods 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<ReturnResult> insert(@RequestBody AmActivityGoods amActivityGoods) {
        amActivityGoodsService.save(amActivityGoods);
        return ResponseEntity.ok(ReturnResult.ok()); 
    }

    /**
     * 修改数据
     *
     * @param amActivityGoods 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<ReturnResult> update(@RequestBody AmActivityGoods amActivityGoods) {
        amActivityGoodsService.updateById(amActivityGoods);
        return ResponseEntity.ok(ReturnResult.ok()); 
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult> delete(@RequestParam("idList") List<Long> idList) {
        amActivityGoodsService.removeByIds(idList);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

