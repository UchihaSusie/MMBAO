package com.rsa.webapi.goods.controller;



import com.rsa.webapi.common.ReturnResult;
import com.rsa.webapi.goods.entity.PageAdjust;
import org.springframework.http.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rsa.webapi.goods.entity.GgPriceAdjust;
import com.rsa.webapi.goods.service.GgPriceAdjustService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理-价格调整(GgPriceAdjust)表控制层
 *
 * @author makejava
 * @since 2024-06-17 22:10:56
 */
@RestController
@RequestMapping("/price")
public class GgPriceAdjustController{
    /**
     * 服务对象
     */
    @Resource
    private GgPriceAdjustService ggPriceAdjustService;
    @GetMapping("/listAll")
    public ResponseEntity<ReturnResult>  listAllPrices () {
        List<GgPriceAdjust> ggPriceAdjusts = ggPriceAdjustService.list();
        return ResponseEntity.ok(ReturnResult.ok(ggPriceAdjusts));
    }


    @PostMapping("/search")
    public ResponseEntity<ReturnResult>  search (@RequestBody PageAdjust pageAdjust) {
        PageAdjust pageAdjustInfo = ggPriceAdjustService.seachByPageAdjust(pageAdjust);
        return ResponseEntity.ok(ReturnResult.ok(pageAdjustInfo));
    }
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        GgPriceAdjust  ggPriceAdjust = this.ggPriceAdjustService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok(ggPriceAdjust));
    }

    /**
     * 新增或修改数据
     *
     * @param ggPriceAdjust 实体对象
     * @return 新增结果
     */
    @PostMapping("save/update")
    public ResponseEntity<ReturnResult>  addOrUpdate(@RequestBody GgPriceAdjust ggPriceAdjust) {
         this.ggPriceAdjustService.saveOrUpdate(ggPriceAdjust);
         return ResponseEntity.ok(ReturnResult.ok(ggPriceAdjust));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult>  delete(@RequestParam("idList") List<Long> idList) {
        this.ggPriceAdjustService.removeByIds(idList);
        return ResponseEntity.ok(ReturnResult.ok());
    }
    
      /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @DeleteMapping("{id}")
    public ResponseEntity<ReturnResult> deleteById(@PathVariable Integer id) {
        this.ggPriceAdjustService.removeById(id);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

