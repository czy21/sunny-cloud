package com.sunny.system.core.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.sunny.framework.file.excel.BaseExcelModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserImport extends BaseExcelModel {
    @NotBlank(message = "姓名不能为空")
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("错误信息")
    private String message;
}
