package com.sunny.cloud.framework.core.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BasePO<T_ID, T_USER> {
    private T_ID id;
    private LocalDateTime createTime;
    private T_USER createUser;
    private LocalDateTime updateTime;
    private T_USER updateUser;
    private Boolean deleted;
    private Integer version;
}
