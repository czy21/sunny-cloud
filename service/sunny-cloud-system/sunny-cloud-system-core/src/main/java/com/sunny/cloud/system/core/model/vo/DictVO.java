package com.sunny.cloud.system.core.model.vo;

import com.sunny.cloud.framework.core.model.CommonValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class DictVO {
    @NotEmpty(message = "id不能为空", groups = CommonValid.Edit.class)
    private String id;
    @Schema(description = "编码")
    @NotEmpty(message = "编码不能为空", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    @Length(max = 200, message = "编码长度不能超过200", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    private String code;
    @Schema(description = "名称")
    @NotEmpty(message = "名称不能为空", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    @Length(max = 200, message = "名称长度不能超过200", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    private String name;
    @NotNull(message = "值类型不能为空", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    private Integer valueType;
    @Schema(description = "备注")
    private String remark;
}
