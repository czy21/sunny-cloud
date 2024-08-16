package com.sunny.framework.file.excel;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EasyExcelProperty {
    private Integer index;
    private List<String> head;

    public EasyExcelProperty(Integer index, List<String> head) {
        this.index = index;
        this.head = head;
    }

    public static EasyExcelProperty of(Integer index, List<String> head) {
        return new EasyExcelProperty(index, head);
    }
}
