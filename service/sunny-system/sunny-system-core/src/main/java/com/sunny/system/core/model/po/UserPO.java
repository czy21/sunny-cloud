package com.sunny.system.core.model.po;

import com.sunny.framework.core.model.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPO extends BasePO<Long, Long> {
    private String name;
}
