package com.sunny.cloud.system.core.model.po;

import com.sunny.cloud.framework.core.model.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPO extends BasePO<Long, Long> {
    private String name;
}
