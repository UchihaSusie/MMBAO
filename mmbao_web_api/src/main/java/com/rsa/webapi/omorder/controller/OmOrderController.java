package com.rsa.webapi.omorder.controller;



import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rsa.webapi.omorder.entity.OmOrder;
import com.rsa.webapi.omorder.entity.OmOrderRequest;
import com.rsa.webapi.omorder.service.OmOrderService;
import com.rsa.webapi.common.ReturnResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单管理-b2c订单(OmOrder)表控制层
 *
 * @author makejava
 * @since 2024-06-12 16:42:31
 */
@RestController
    @RequestMapping("/order")
public class OmOrderController  {
    /**
     * 服务对象
     */
    @Resource
    private OmOrderService omOrderService;
    Logger logger = LogManager.getLogger(OmOrderController.class);


    /**
     * 导出excel文件
     */
    @GetMapping("/export")
    public void exportWithEasyExcel( Integer  id){
        omOrderService.exportFileByEasyExcel(id);

    }


    /**
     * 导入excel文件添加数据
     */
    @PostMapping("/import")
    public ResponseEntity<ReturnResult> importWithEasyExcel(@RequestParam("file")MultipartFile file){
        boolean b = omOrderService.saveFileByEasyExcel(file);
        return ResponseEntity.ok(ReturnResult.ok(b));
    }

    /**
     * 分页查询所有数据
     *
     * @return 所有数据
     */
    @PostMapping("/list")
    public ResponseEntity<ReturnResult> selectAll(@RequestBody OmOrderRequest request) {
        Page<OmOrder> orderPage = omOrderService.listByPage(request);
        return ResponseEntity.ok(ReturnResult.ok(orderPage));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据包括商品列表
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ReturnResult> selectOne(@PathVariable Integer id) {
        OmOrder order = omOrderService.getByIdWithGoods(id);
        return ResponseEntity.ok(ReturnResult.ok(order));
    }

    /**
     * 新增数据
     *
     * @param omOrder 实体对象
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<ReturnResult> insert(@RequestBody OmOrder omOrder) {
        boolean b = omOrderService.save(omOrder);
        return ResponseEntity.ok(ReturnResult.ok(b));
    }

    /**
     * 修改数据(出库)
     */
    @GetMapping  ("/edit")
    public ResponseEntity<ReturnResult> update( Integer id, Integer orderState) {

        boolean b = omOrderService.updateByIdWithSattus(id,orderState);
        return ResponseEntity.ok(ReturnResult.ok(b));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public ResponseEntity<ReturnResult> delete(@RequestParam("idList") List<Long> idList) {
        boolean b = omOrderService.removeByIds(idList);
        return ResponseEntity.ok(ReturnResult.ok(b));
    }
}

