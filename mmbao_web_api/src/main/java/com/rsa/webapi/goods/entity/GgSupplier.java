package com.rsa.webapi.goods.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品管理-供应商(GgSupplier)表实体类
 *
 * @author makejava
 * @since 2024-06-14 23:07:25
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GgSupplier extends Model<GgSupplier> {
    
    private Integer id;
    
    private String supplierName;
    
    private String contacts;
    
    private String contactPhone;
    
    private String contactEmail;
    
    private String contactAddress;
    
    private String bankOfDeposit;
    
    private String accountNo;
    
    private Date updateTime;
    
    private Date createTime;

}

