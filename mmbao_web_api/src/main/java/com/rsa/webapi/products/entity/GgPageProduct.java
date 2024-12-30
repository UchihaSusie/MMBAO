package com.rsa.webapi.products.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GgPageProduct {
    /**
     * 总页数
     */
    private Integer pageTotal;
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 查询条件
     */
    private GgProduct ggProduct;
    /**
     * 当前页面数据
     */
    private List<GgProduct> proList;


}
