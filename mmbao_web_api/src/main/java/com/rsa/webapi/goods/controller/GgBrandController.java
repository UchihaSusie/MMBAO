package com.rsa.webapi.goods.controller;



import com.rsa.webapi.common.ReturnResult;
import com.rsa.webapi.goods.entity.PageBrand;
import org.springframework.http.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rsa.webapi.goods.entity.GgBrand;
import com.rsa.webapi.goods.service.GgBrandService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理-品牌管理(GgBrand)表控制层
 *
 * @author makejava
 * @since 2024-06-14 15:20:20
 */
@RestController
@RequestMapping("/brand")
public class GgBrandController{
    /**
     * 服务对象
     */
    @Resource
    private GgBrandService ggBrandService;

    @GetMapping("/listAll")
    public ResponseEntity<ReturnResult>  listAllBrand () {
        List<GgBrand> ggBrandList = ggBrandService.list();
        return ResponseEntity.ok(ReturnResult.ok(ggBrandList));
    }

    @PostMapping("/brandByType")
    public ResponseEntity<ReturnResult>  brandByTypeId (Integer id) {
        List<GgBrand> ggBrandList = ggBrandService.getBrandByTypeId(id);
        return ResponseEntity.ok(ReturnResult.ok(ggBrandList));
    }

    @PostMapping("/search")
    public ResponseEntity<ReturnResult>  search (@RequestBody PageBrand pageBrand) {
        PageBrand pageBrandInfo = ggBrandService.seachByPageBrand(pageBrand);
        return ResponseEntity.ok(ReturnResult.ok(pageBrandInfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        GgBrand  ggBrand = this.ggBrandService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok(ggBrand));
    }

    /**
     * 新增或修改数据
     *
     * @param ggBrand 实体对象
     * @return 新增结果
     */
    @PostMapping("save/update")
    public ResponseEntity<ReturnResult>  addOrUpdate(@RequestBody GgBrand ggBrand) {
         this.ggBrandService.saveOrUpdate(ggBrand);
         return ResponseEntity.ok(ReturnResult.ok(ggBrand));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult>  delete(@RequestParam("idList") List<Long> idList) {
        this.ggBrandService.removeByIds(idList);
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
        this.ggBrandService.removeById(id);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

