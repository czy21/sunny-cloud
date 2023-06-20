package com.sunny.cloud.system.core.model.po;

import lombok.Data;

/**
 * 文件存储目标表
 *
 * @TableName sys_file_target
 */
@Data
public class FileTargetPO {
    private String key;

    /**
     * 外部地址 需要带完整访问地址
     */
    private String rootUrl;

    /**
     * 内部地址 需要带相对地址
     */
    private String rootPath;

    /**
     * 存储目标类型(OSS对象存储，LOCAL本地存储)
     */
    private String targetKind;
}