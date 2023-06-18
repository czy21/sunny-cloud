package com.sunny.cloud.system.core.model.po;

import com.sunny.cloud.framework.core.model.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司表
 *
 * @TableName file
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FilePO extends BasePO<Long, Long> {

    /**
     * 文件名称
     */
    private String name;

    /**
     * 唯一文件名(uuid.*)
     */
    private String path;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件存储目标Key
     */
    private String fileTargetKey;

}