package com.sunny.cloud.system.core.model.vo;

import com.sunny.cloud.framework.core.model.CommonValid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class DictVO {
    private String id;
    @NotEmpty(message = "编码不能为空", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    @Length(max = 200, message = "编码长度不能超过200", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    private String code;
    @NotEmpty(message = "名称不能为空", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    @Length(max = 200, message = "名称长度不能超过200", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    private String name;
}
