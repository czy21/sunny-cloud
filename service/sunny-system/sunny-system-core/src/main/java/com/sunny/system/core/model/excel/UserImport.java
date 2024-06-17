package com.sunny.system.core.model.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.sunny.framework.file.excel.BaseExcelDataModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserImport extends BaseExcelDataModel {
    @NotBlank(message = "姓名不能为空")
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年龄")
    private Integer age;
}
