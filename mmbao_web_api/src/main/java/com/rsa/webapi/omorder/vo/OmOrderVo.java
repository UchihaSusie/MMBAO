package com.rsa.webapi.omorder.vo;

import com.rsa.webapi.omorder.entity.CreatorInfo;
import com.rsa.webapi.omorder.entity.OmOrder;
import com.rsa.webapi.omorder.entity.OmOrderGoods;
import lombok.Data;

import java.util.List;

/**
 * @author : cgq
 * @className: OmOrderVo
 * @description:
 * @date: 2024/6/16 20:55
 * @version: 0.1
 * @since: jdk11
 */
@Data
public class OmOrderVo {
    private List<OmOrder> order;
    private List<OmOrderGoods> omOrderGoods;
    private CreatorInfo creatorInfo;
    private String url;
}
