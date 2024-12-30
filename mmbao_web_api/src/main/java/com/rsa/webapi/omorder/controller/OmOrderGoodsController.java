package com.rsa.webapi.omorder.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rsa.webapi.omorder.entity.OmOrderGoods;
import com.rsa.webapi.omorder.service.OmOrderGoodsService;
import com.rsa.webapi.common.ReturnResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 订单管理-b2c订单与商品关系表
(OmOrderGoods)表控制层
 *
 * @author makejava
 * @since 2024-06-13 15:18:13
 */
@RestController
@RequestMapping("omOrderGoods")
public class OmOrderGoodsController  {
    /**
     * 服务对象
     */
    @Resource
    private OmOrderGoodsService omOrderGoodsService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param omOrderGoods 查询实体
     * @return 所有数据
     */
    @GetMapping
    public ResponseEntity<ReturnResult> selectAll(Page<OmOrderGoods> page, OmOrderGoods omOrderGoods) {
        omOrderGoodsService.page(page, new QueryWrapper<>(omOrderGoods));
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
        omOrderGoodsService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok()); 
    }

    /**
     * 新增数据
     *
     * @param omOrderGoods 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<ReturnResult> insert(@RequestBody OmOrderGoods omOrderGoods) {
        omOrderGoodsService.save(omOrderGoods);
        return ResponseEntity.ok(ReturnResult.ok()); 
    }

    /**
     * 修改数据
     *
     * @param omOrderGoods 实体对象
     * @return 修改结果
     */
    @PutMapping
    public ResponseEntity<ReturnResult> update(@RequestBody OmOrderGoods omOrderGoods) {
        omOrderGoodsService.updateById(omOrderGoods);
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
        omOrderGoodsService.removeByIds(idList);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

