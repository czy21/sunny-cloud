package com.sunny.order.core.model.po;

import java.math.BigDecimal;

import com.sunny.framework.core.model.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderPO extends BasePO<Long,String> {
    /**
     * 
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人手机
     */
    private String receiverPhone;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 省名称
     */
    private String provinceName;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区编码
     */
    private String districtCode;

    /**
     * 区名称
     */
    private String districtName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 订单状态
     */
    private String state;

    /**
     * 支付金额
     */
    private BigDecimal paymentAmount;

    /**
     * 支付状态
     */
    private Integer paymentStatus;

}