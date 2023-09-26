package com.sunny.system.core.model.po;

import com.sunny.framework.core.model.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典值
 *
 * @TableName sys_dict_value
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictValuePO extends BasePO<Long, Long> {
    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 字典主键
     */
    private Long dictId;


}