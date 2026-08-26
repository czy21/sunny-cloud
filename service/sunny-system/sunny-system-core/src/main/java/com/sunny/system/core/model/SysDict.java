package com.sunny.system.core.model;

import jakarta.annotation.Generated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Database Table Remarks:
 *   字典
 */
@Data
@Accessors(chain=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysDict {
    /**
     * Database Column Remarks:
     *   主键自增
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict.id")
    private Long id;

    /**
     * Database Column Remarks:
     *   编码
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict.code")
    private String code;

    /**
     * Database Column Remarks:
     *   名称
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict.name")
    private String name;

    /**
     * Database Column Remarks:
     *   描述
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict.desc")
    private String desc;

    /**
     * Database Column Remarks:
     *   值类型;0-Int,1-String
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict.value_type")
    private Integer valueType;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict.create_time")
    private LocalDateTime createTime;

    /**
     * Database Column Remarks:
     *   创建人
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict.create_user")
    private String createUser;

    /**
     * Database Column Remarks:
     *   更新时间
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict.update_time")
    private LocalDateTime updateTime;

    /**
     * Database Column Remarks:
     *   更新人
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict.update_user")
    private String updateUser;

    /**
     * Database Column Remarks:
     *   是否删除
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict.deleted")
    private Boolean deleted;
}