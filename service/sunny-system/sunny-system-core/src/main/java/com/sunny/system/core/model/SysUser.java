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
 *   用户表
 */
@Data
@Accessors(chain=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    /**
     * Database Column Remarks:
     *   
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.id")
    private Long id;

    /**
     * Database Column Remarks:
     *   用户名
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.username")
    private String username;

    /**
     * Database Column Remarks:
     *   密码
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.password")
    private String password;

    /**
     * Database Column Remarks:
     *   名字
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.name")
    private String name;

    /**
     * Database Column Remarks:
     *   手机
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.phone")
    private String phone;

    /**
     * Database Column Remarks:
     *   邮箱
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.email")
    private String email;

    /**
     * Database Column Remarks:
     *   地址
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.address")
    private String address;

    /**
     * Database Column Remarks:
     *   创建时间
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.create_time")
    private LocalDateTime createTime;

    /**
     * Database Column Remarks:
     *   创建人
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.create_user")
    private String createUser;

    /**
     * Database Column Remarks:
     *   更新时间
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.update_time")
    private LocalDateTime updateTime;

    /**
     * Database Column Remarks:
     *   更新人
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.update_user")
    private String updateUser;

    /**
     * Database Column Remarks:
     *   是否删除
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: sys_user.deleted")
    private Boolean deleted;
}