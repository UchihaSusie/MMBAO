package com.rsa.webapi.omorder.entity;

import java.time.LocalDate;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.List;

import lombok.*;

/**
 * 订单管理-b2c订单(OmOrder)表实体类
 *
 * @author makejava
 * @since 2024-06-12 16:42:31
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OmOrder extends Model<OmOrder> {
    @TableId(type = IdType.AUTO)
    private Integer id;
    //订单编号
    private Integer orderCode;
    //下订单的时间
    private LocalDate orderTime;
    //订单类型
    private Integer orderType;
    //订单动作
    private Integer orderAction;
    //业务类型
    private Integer bussinessType;
    //订单状态
    private Integer orderState;
    //收货人姓名
    private String cneeName;
    //会员账号
    private String cneeAccount;
    //联系电话
    private String cneePhone;
    //联系手机
    private String cneeCellphone;
    //邮政编码
    private Integer cneePostalCode;
    //收货地址
    private String cneeAddress;
    //配送地区
    private String cneeDeliArea;
    //配送方式
    private Integer cneeDeliWay;
    //配送费用
    private String cneeDeliFare;
    //支付方式
    private Integer cneeDeliMethod;
    //自提时间
    private Date cneePickTime;
    //电子邮箱
    private String cneeEmail;
    //汇款人
    private String remittor;
    //备注
    private String remark;
    /**
     * 该订单的商品列表
     */
    @TableField(exist = false)
    private List<OmOrderGoods> omOrderGoodsList;
    /**
     * 商品清单字符串
     */
    @TableField(exist = false)
    private String goodsList;
    /**
     * 时间条件查询开始时间与结束时间
     */
    @TableField(exist = false)
    private LocalDate startDate;
    @TableField(exist = false)
    private LocalDate endDate;
}

