package com.rsa.webapi.products.controller;



import com.rsa.webapi.common.ReturnResult;
import com.rsa.webapi.products.entity.GgPageProduct;
import org.springframework.http.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rsa.webapi.products.entity.GgProduct;
import com.rsa.webapi.products.service.GgProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理-产品查询
(GgProduct)表控制层
 *
 * @author makejava
 * @since 2024-06-13 14:56:16
 */
@RestController
@RequestMapping("/product")
public class GgProductController{
    /**
     * 服务对象
     */
    @Resource
    private GgProductService ggProductService;

    @GetMapping("/listAll")
    public ResponseEntity<ReturnResult> queryList(){
        List<GgProduct> listProducts = ggProductService.list();
        return ResponseEntity.ok(ReturnResult.ok(listProducts));
    }

    @PostMapping("/search")
    public ResponseEntity<ReturnResult>  search (@RequestBody GgPageProduct ggPageProduct) {
        GgPageProduct ggPageProductinfo = ggProductService.seachByProduct(ggPageProduct);
        return ResponseEntity.ok(ReturnResult.ok(ggPageProductinfo));
    }

    @PostMapping("/code")
    public ResponseEntity<ReturnResult> selectOne(String coding) {
        GgProduct ggProduct = ggProductService.getByCode(coding);
        return ResponseEntity.ok(ReturnResult.ok(ggProduct));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        GgProduct  ggProduct = this.ggProductService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok(ggProduct));
    }

    /**
     * 新增或修改数据
     *
     * @param ggProduct 实体对象
     * @return 新增结果
     */
    @PostMapping("save/update")
    public ResponseEntity<ReturnResult>  addOrUpdate(@RequestBody GgProduct ggProduct) {
         this.ggProductService.saveOrUpdate(ggProduct);
         return ResponseEntity.ok(ReturnResult.ok(ggProduct));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult>  delete(@RequestParam("idList") List<Long> idList) {
        this.ggProductService.removeByIds(idList);
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
        this.ggProductService.removeById(id);
        return ResponseEntity.ok(ReturnResult.ok());
    }
}

