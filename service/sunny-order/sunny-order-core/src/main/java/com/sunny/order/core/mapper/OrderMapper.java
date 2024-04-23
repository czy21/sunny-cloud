package com.sunny.order.core.mapper;

import com.sunny.order.core.model.po.OrderPO;

/**
* @author chenzhaoyu
* @description 针对表【order】的数据库操作Mapper
* @createDate 2024-04-23 09:11:27
* @Entity generator.domain.order
*/
public interface OrderMapper {

    int insert(OrderPO record);

    int update(OrderPO record);

}
