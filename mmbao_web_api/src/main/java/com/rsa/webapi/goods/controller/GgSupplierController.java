package com.rsa.webapi.goods.controller;



import com.rsa.webapi.common.ReturnResult;
import com.rsa.webapi.goods.entity.*;
import com.rsa.webapi.goods.entity.GgGoods;
import com.rsa.webapi.goods.entity.GgSupplier;
import com.rsa.webapi.goods.entity.PageSupplier;
import org.springframework.http.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rsa.webapi.goods.service.GgSupplierService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品管理-供应商(GgSupplier)表控制层
 *
 * @author makejava
 * @since 2024-06-14 23:07:25
 */
@RestController
@RequestMapping("/supplier")
public class GgSupplierController{
    /**
     * 服务对象
     */
    @Resource
    private GgSupplierService ggSupplierService;


    @PostMapping("/goodBySupply/{id}")
    public ResponseEntity<ReturnResult>  goodBySupplyId (@PathVariable Integer id) {
        List<GgGoods> ggGoodsList = ggSupplierService.goodBySupplyId(id);
        return ResponseEntity.ok(ReturnResult.ok(ggGoodsList));
    }


    @PostMapping("/search")
    public ResponseEntity<ReturnResult>  search (@RequestBody PageSupplier pageSupplier) {
        PageSupplier pageSupplierInfo = ggSupplierService.seachByPageSupplier(pageSupplier);
        return ResponseEntity.ok(ReturnResult.ok(pageSupplierInfo));
    }
    @PostMapping("/deleteGoods")
    public ResponseEntity<ReturnResult> deleteGoodId(Integer gid,Integer sid) {
        ggSupplierService.deleteGoodId(gid, sid);
        return ResponseEntity.ok(ReturnResult.ok());
    }

    @PostMapping("/addGoods")
    public ResponseEntity<ReturnResult> addGoodId(Integer gid,Integer sid) {
        ggSupplierService.addGoodId(gid, sid);
        return ResponseEntity.ok(ReturnResult.ok());
    }



    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        GgSupplier ggSupplier = this.ggSupplierService.getById(id);
        return ResponseEntity.ok(ReturnResult.ok(ggSupplier));
    }

    /**
     * 新增或修改数据
     *
     * @param ggSupplier 实体对象
     * @return 新增结果
     */
    @PostMapping("save/update")
    public ResponseEntity<ReturnResult>  addOrUpdate(@RequestBody GgSupplier ggSupplier) {
         this.ggSupplierService.saveOrUpdate(ggSupplier);
         return ResponseEntity.ok(ReturnResult.ok(ggSupplier));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult>  delete(@RequestParam("idList") List<Long> idList) {
        this.ggSupplierService.removeByIds(idList);
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
        this.ggSupplierService.removeById(id);
        return ResponseEntity.ok(ReturnResult.ok());
    }

}

