package com.sunny.system.core.model.po;

import com.sunny.framework.core.model.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典
 *
 * @TableName sys_dict
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictPO extends BasePO<Long, Long> {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String remark;

    /**
     * 值类型;0-Int,1-String
     */
    private Integer valueType;


}