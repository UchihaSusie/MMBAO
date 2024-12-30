package com.rsa.webapi.goods.controller;



import com.rsa.webapi.common.ReturnResult;
import com.rsa.webapi.goods.entity.PageType;
import org.springframework.http.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rsa.webapi.goods.entity.GgGoodsType;
import com.rsa.webapi.goods.service.GgGoodsTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理-商品类型表(GgGoodsType)表控制层
 *
 * @author makejava
 * @since 2024-06-14 15:12:28
 */
@RestController
@RequestMapping("/type")
public class GgGoodsTypeController{
    /**
     * 服务对象
     */
    @Resource
    private GgGoodsTypeService ggGoodsTypeService;

    @PostMapping("/search")
    public ResponseEntity<ReturnResult>  search (@RequestBody PageType pageType) {
        PageType pageTypeInfo = ggGoodsTypeService.seachByPageType(pageType);
        return ResponseEntity.ok(ReturnResult.ok(pageTypeInfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        GgGoodsType  ggGoodsType = this.ggGoodsTypeService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok(ggGoodsType));
    }

    /**
     * 新增或修改数据
     *
     * @param ggGoodsType 实体对象
     * @return 新增结果
     */
    @PostMapping("save/update")
    public ResponseEntity<ReturnResult>  addOrUpdate(@RequestBody GgGoodsType ggGoodsType) {
         this.ggGoodsTypeService.saveOrUpdate(ggGoodsType);
         return ResponseEntity.ok(ReturnResult.ok(ggGoodsType));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult>  delete(@RequestParam("idList") List<Long> idList) {
        this.ggGoodsTypeService.removeByIds(idList);
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
        this.ggGoodsTypeService.removeById(id);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

