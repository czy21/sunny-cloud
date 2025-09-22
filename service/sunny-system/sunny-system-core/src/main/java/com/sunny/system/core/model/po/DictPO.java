package com.sunny.system.core.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 字典
 */
@Data
@Accessors(chain=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictPO {
    /**
     * 主键自增
     */
    private Long id;

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
    private String desc;

    /**
     * 值类型;0-Int,1-String
     */
    private Boolean valueType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 是否删除
     */
    private Boolean deleted;
}