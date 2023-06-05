package com.sunny.cloud.system.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysUserDTO {
    @Schema(description = "姓名")
    private String name;
}
