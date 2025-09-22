package com.sunny.system.core.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 字典值
 */
@Data
@Accessors(chain=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictValuePO {
    /**
     * 主键自增
     */
    private Long id;

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