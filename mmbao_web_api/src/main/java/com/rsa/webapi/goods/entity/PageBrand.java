package com.rsa.webapi.goods.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBrand {
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
    private GgBrand ggBrand;
    /**
     * 当前页面数据
     */
    private List<GgBrand> brandList;
}
