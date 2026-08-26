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
 *   文件表
 */
@Data
@Accessors(chain=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysFile {
    /**
     * Database Column Remarks:
     *   
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_file.id")
    private String id;

    /**
     * Database Column Remarks:
     *   
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_file.name")
    private String name;

    /**
     * Database Column Remarks:
     *   
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_file.path")
    private String path;

    /**
     * Database Column Remarks:
     *   
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_file.type")
    private String type;

    /**
     * Database Column Remarks:
     *   
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_file.target_key")
    private String targetKey;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_file.create_time")
    private LocalDateTime createTime;

    /**
     * Database Column Remarks:
     *   创建人
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_file.create_user")
    private String createUser;

    /**
     * Database Column Remarks:
     *   更新时间
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_file.update_time")
    private LocalDateTime updateTime;

    /**
     * Database Column Remarks:
     *   更新人
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_file.update_user")
    private String updateUser;

    /**
     * Database Column Remarks:
     *   是否删除
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_file.deleted")
    private Boolean deleted;
}