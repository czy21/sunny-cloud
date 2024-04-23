package com.sunny.order.core.mapper;

import com.sunny.order.core.model.po.OrderPO;


public interface OrderMapper {

    int insert(OrderPO record);

    int update(OrderPO record);

}
