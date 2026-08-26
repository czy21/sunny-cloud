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
 *   字典值
 */
@Data
@Accessors(chain=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysDictValue {
    /**
     * Database Column Remarks:
     *   主键自增
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict_value.id")
    private Long id;

    /**
     * Database Column Remarks:
     *   名称
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict_value.name")
    private String name;

    /**
     * Database Column Remarks:
     *   值
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict_value.value")
    private String value;

    /**
     * Database Column Remarks:
     *   排序
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict_value.sort")
    private Integer sort;

    /**
     * Database Column Remarks:
     *   字典主键
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict_value.dict_id")
    private Long dictId;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict_value.create_time")
    private LocalDateTime createTime;

    /**
     * Database Column Remarks:
     *   创建人
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict_value.create_user")
    private String createUser;

    /**
     * Database Column Remarks:
     *   更新时间
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict_value.update_time")
    private LocalDateTime updateTime;

    /**
     * Database Column Remarks:
     *   更新人
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict_value.update_user")
    private String updateUser;

    /**
     * Database Column Remarks:
     *   是否删除
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_dict_value.deleted")
    private Boolean deleted;
}