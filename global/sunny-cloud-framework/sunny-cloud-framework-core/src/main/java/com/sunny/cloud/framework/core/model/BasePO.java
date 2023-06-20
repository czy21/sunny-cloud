package com.sunny.cloud.framework.core.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BasePO<TID, U> {
    private TID id;
    private LocalDateTime createTime;
    private U createUser;
    private LocalDateTime updateTime;
    private U updateUser;
    private Boolean deleted;
}
