package com.sunny.system.core.model.vo;

import com.sunny.framework.core.model.CommonValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class DictValueVO {
    @NotNull(message = "id不能为空", groups = CommonValid.Edit.class)
    private Long id;
    @NotEmpty(message = "名称不能为空", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    @Length(max = 100, message = "名称长度不能超过100", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    private String name;
    @NotEmpty(message = "值不能为空", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    @Length(max = 100, message = "值长度不能超过100", groups = {CommonValid.Add.class, CommonValid.Edit.class})
    private String value;
    private Integer sort;
    private Long dictId;
}
