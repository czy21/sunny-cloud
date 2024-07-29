package com.sunny.system.core.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    @Schema(description="主键自增")
    private Long id;
    @Schema(description="用户名")
    private String username;
    @Schema(description="密码")
    private String password;
    @Schema(description="名字")
    private String name;
    @Schema(description="手机号")
    private String phone;
    @Schema(description="邮箱")
    private String email;
    @Schema(description="地址")
    private String address;
    @Schema(description="创建时间")
    private LocalDateTime createTime;
    @Schema(description="创建人")
    private Long createUser;
    @Schema(description="更新时间")
    private LocalDateTime updateTime;
    @Schema(description="更新人")
    private Long updateUser;
    @Schema(description="是否删除")
    private Boolean deleted;
}
