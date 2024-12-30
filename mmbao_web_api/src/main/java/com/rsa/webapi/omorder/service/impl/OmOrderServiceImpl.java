package com.rsa.webapi.omorder.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rsa.webapi.common.ExceptionEnum;
import com.rsa.webapi.common.exception.JavasmException;
import com.rsa.webapi.omorder.dao.OmOrderDao;
import com.rsa.webapi.omorder.entity.OmOrder;
import com.rsa.webapi.omorder.entity.OmOrderGoods;
import com.rsa.webapi.omorder.entity.OmOrderRequest;
import com.rsa.webapi.omorder.service.OmOrderGoodsService;
import com.rsa.webapi.omorder.service.OmOrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单管理-b2c订单(OmOrder)表服务实现类
 *
 * @author makejava
 * @since 2024-06-12 16:42:31
 */
@Service("omOrderService")
public class OmOrderServiceImpl extends ServiceImpl<OmOrderDao, OmOrder> implements OmOrderService {

    @Resource
    OmOrderGoodsService omOrderGoodsService;
    @Resource
    HttpServletResponse response;
    /**
     * B2C订单导入
     */
    @Override
    public boolean saveFileByEasyExcel(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),OmOrder.class,new ReadListener<OmOrder>() {
                private static  final  int save_size = 1000;
                private List<OmOrder> saveList = ListUtils.newArrayListWithExpectedSize(save_size);
                @Override
                public void invoke(OmOrder order, AnalysisContext analysisContext) {
                    saveList.add(order);
                    if (saveList.size()>=save_size){
                        //保存
                        saveData();
                        saveList = ListUtils.newArrayListWithExpectedSize(save_size);
                    }
                }
                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                    //保存到数据库
                    saveData();
                }
                private void saveData(){
                    saveBatch(saveList);
                }
            }).sheet().doRead();
            EasyExcel.read(file.getInputStream(),OmOrderGoods.class,new ReadListener<OmOrderGoods>() {
                private static  final  int save_size = 1000;
                private List<OmOrderGoods> saveList1 = ListUtils.newArrayListWithExpectedSize(save_size);
                @Override
                public void invoke(OmOrderGoods omOrderGoods, AnalysisContext analysisContext) {
                    saveList1.add(omOrderGoods);
                    if (saveList1.size()>=save_size){
                        //保存
                        saveData();
                        saveList1 = ListUtils.newArrayListWithExpectedSize(save_size);
                    }
                }
                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                    //保存到数据库
                    saveData();
                }
                private void saveData(){
                    omOrderGoodsService.saveBatch(saveList1);
                }
            }).sheet(1).doRead();
        } catch (IOException e) {
            throw new JavasmException(ExceptionEnum.FILE_ADDRESS_ERROR);
        }
        return true;
    }

    @Override
    public OmOrder getByIdWithGoods(Integer id) {
        OmOrder omOrder = getById(id);
        Integer orderCode = omOrder.getOrderCode();
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_code",orderCode);
        List list = omOrderGoodsService.list(queryWrapper);
        omOrder.setOmOrderGoodsList(list);
        return omOrder;
    }

    @Override
    public boolean updateByIdWithSattus(Integer id, Integer orderState) {
        UpdateWrapper<OmOrder> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("order_state",orderState);
        boolean update = update(wrapper);
        return update;
    }

    /**
     * excel文件导出
     */
    @Override
    public void exportFileByEasyExcel(Integer  id) {
        //订单表
        OmOrder omOrder = getById(id);
        List<OmOrder> orderList = new ArrayList<>();
        orderList.add(omOrder);
        //根据订单id 查询 商品列表
        List<OmOrderGoods> orderCodeList = omOrderGoodsService.getByOrderCode(omOrder.getOrderCode());

        // 确保文件名唯一性
        String fileName = "订单详情_" + System.currentTimeMillis() + ".xlsx";
        // 使用ByteArrayOutputStream在内存中处理Excel
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet(0, "订单表").build();
            WriteSheet writeSheet2 = EasyExcel.writerSheet(1, "商品表").build();
            excelWriter.write(orderList,writeSheet);
            excelWriter.write(orderCodeList,writeSheet2);
            fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+","%20");
            response.setHeader("Content-disposition","attachment;filename*=utf-8''" + fileName);
            response.setContentType("application/vnd.ms-excel");
            excelWriter.finish();
            response.flushBuffer();
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
        }
    }

    /**
     * 分页查询附带条件信息
     */
    @Override
    public Page<OmOrder> listByPage(OmOrderRequest request) {
        // 初始化分页参数，默认值
        Integer pageNum = Optional.ofNullable(request.getPageNum()).orElse(1);
        Integer pageSize = Optional.ofNullable(request.getPageSize()).orElse(9);
        Page<OmOrder> page = new Page<>(pageNum, pageSize);

        // 构建查询条件
        OmOrder omOrder = request.getOmOrder();
        QueryWrapper<OmOrder> queryWrapper = new QueryWrapper<>(omOrder);
        applyTimeRangeCondition(queryWrapper, omOrder);

        // 执行分页查询
        Page<OmOrder> orderPage = page(page, queryWrapper);
        enrichOrderWithGoodsInfo(orderPage.getRecords());

        return orderPage;
    }


    /**
     * 添加时间条件查询参数
     */
    private void applyTimeRangeCondition(QueryWrapper<OmOrder> queryWrapper, OmOrder omOrder) {
        LocalDate startDate = omOrder.getStartDate();
        LocalDate endDate = omOrder.getEndDate();
        if (startDate != null && endDate != null) {
            queryWrapper.between("order_time", startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        }
    }

    /**
     * 遍历订单列表
     */
    private void enrichOrderWithGoodsInfo(List<OmOrder> orders) {
        orders.forEach(this::fetchAndSetGoodsInfo);
    }

    /**
     * 根据订单代码查询并加入相关商品信息
     */
    private void fetchAndSetGoodsInfo(OmOrder order) {
        List<OmOrderGoods> goodsList = omOrderGoodsService.getByOrderCode(order.getOrderCode());
        order.setOmOrderGoodsList(goodsList);

        if (!goodsList.isEmpty()) {
            String goodsNames = goodsList.stream()
                    .map(OmOrderGoods::getGoodsName)
                    .collect(Collectors.joining(", "));
            order.setGoodsList(goodsNames);
        } else {
            // 如果没有商品，设定一个默认值或者留空
            order.setGoodsList("");
        }
    }
}

