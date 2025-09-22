package com.sunny.system.core.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户表
 */
@Data
@Accessors(chain=true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPO {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 名字
     */
    private String name;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

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

    /**
     * 密码
     */
    private String password;
}