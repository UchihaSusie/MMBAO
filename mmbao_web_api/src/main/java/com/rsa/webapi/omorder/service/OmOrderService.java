package com.rsa.webapi.omorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rsa.webapi.omorder.entity.OmOrder;
import com.rsa.webapi.omorder.entity.OmOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 订单管理-b2c订单(OmOrder)表服务接口
 *
 * @author makejava
 * @since 2024-06-12 16:42:31
 */
public interface OmOrderService extends IService<OmOrder> {
    /**
     * 分页条件查询
     * @param request OmOrderRequest request
     * @return 分页菜单列表
     */
    Page<OmOrder> listByPage( OmOrderRequest request);

    /**
     * 导入excel文件录入数据库
     * @param file xmsl格式
     * @return 返回成功与否
     */
    boolean saveFileByEasyExcel(MultipartFile file);
    /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据包括商品列表
     */
    OmOrder getByIdWithGoods(Integer id);

    boolean updateByIdWithSattus(Integer id, Integer orderState);

    void exportFileByEasyExcel(Integer  id);
}

