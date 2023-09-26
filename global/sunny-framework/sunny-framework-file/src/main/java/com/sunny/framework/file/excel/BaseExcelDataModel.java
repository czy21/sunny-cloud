package com.sunny.framework.file.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Data;

@Data
public class BaseExcelDataModel {
    @ExcelIgnore
    private Integer rowIndex;
    @ExcelIgnore
    private String message;
}
