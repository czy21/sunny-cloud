package com.sunny.cloud.system.core.model.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DictVO {
    private String id;
    @NotBlank(message = "编码不能为空")
    private String code;
    @NotBlank(message = "姓名不能为空", groups = EditValid.class)
    private String name;

    public static class AddValid{

    }
    public static class EditValid{

    }
}
