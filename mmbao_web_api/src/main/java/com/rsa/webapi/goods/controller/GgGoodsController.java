package com.rsa.webapi.goods.controller;



import com.rsa.webapi.common.ReturnResult;
import com.rsa.webapi.goods.entity.PageGoods;
import org.springframework.http.ResponseEntity;
import com.rsa.webapi.goods.entity.GgGoods;
import com.rsa.webapi.goods.service.GgGoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理-商品管理(GgGoods)表控制层
 *
 * @author makejava
 * @since 2024-06-13 15:23:45
 */
@RestController
@RequestMapping("/goods")
public class GgGoodsController{
    /**
     * 服务对象
     */
    @Resource
    private GgGoodsService ggGoodsService;


    @PostMapping("/search")
    public ResponseEntity<ReturnResult>  search (@RequestBody PageGoods pageGoods) {
        PageGoods pageGoodsInfo = ggGoodsService.seachByPageGood(pageGoods);
        return ResponseEntity.ok(ReturnResult.ok(pageGoodsInfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        GgGoods  ggGoods = this.ggGoodsService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok(ggGoods));
    }

    /**
     * 新增或修改数据
     *
     * @param ggGoods 实体对象
     * @return 新增结果
     */
    @PostMapping("save/update")
    public ResponseEntity<ReturnResult>  addOrUpdate(@RequestBody GgGoods ggGoods) {
         this.ggGoodsService.saveOrUpdate(ggGoods);
         return ResponseEntity.ok(ReturnResult.ok(ggGoods));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult>  delete(@RequestParam("idList") List<Long> idList) {
        this.ggGoodsService.removeByIds(idList);
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
        this.ggGoodsService.removeById(id);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

