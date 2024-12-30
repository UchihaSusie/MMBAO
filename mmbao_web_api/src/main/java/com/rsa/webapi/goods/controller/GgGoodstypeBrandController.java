package com.rsa.webapi.goods.controller;



import com.rsa.webapi.common.ReturnResult;
import org.springframework.http.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rsa.webapi.goods.entity.GgGoodstypeBrand;
import com.rsa.webapi.goods.service.GgGoodstypeBrandService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理-商品类型与品牌关系表(GgGoodstypeBrand)表控制层
 *
 * @author makejava
 * @since 2024-06-17 10:08:05
 */
@RestController
@RequestMapping("/goodBrand")
public class GgGoodstypeBrandController{
    /**
     * 服务对象
     */
    @Resource
    private GgGoodstypeBrandService ggGoodstypeBrandService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        GgGoodstypeBrand  ggGoodstypeBrand = this.ggGoodstypeBrandService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok(ggGoodstypeBrand));
    }

    /**
     * 新增或修改数据
     *
     * @param  ggGoodstypeBrand 实体对象
     * @return 新增结果
     */
    @PostMapping("saveBrands")
    public ResponseEntity<ReturnResult>  saveBrandsList(@RequestBody GgGoodstypeBrand ggGoodstypeBrand) {
        this.ggGoodstypeBrandService.saveBrandsList(ggGoodstypeBrand);
        return ResponseEntity.ok(ReturnResult.ok());
    }


    /**
     * 新增或修改数据
     *
     * @param ggGoodstypeBrand 实体对象
     * @return 新增结果
     */
    @PostMapping("save/update")
    public ResponseEntity<ReturnResult>  addOrUpdate(@RequestBody GgGoodstypeBrand ggGoodstypeBrand) {
         this.ggGoodstypeBrandService.saveOrUpdate(ggGoodstypeBrand);
         return ResponseEntity.ok(ReturnResult.ok(ggGoodstypeBrand));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult>  delete(@RequestParam("idList") List<Long> idList) {
        this.ggGoodstypeBrandService.removeByIds(idList);
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
        this.ggGoodstypeBrandService.removeById(id);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

