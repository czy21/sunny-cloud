package com.sunny.system.core.model.query;

import com.sunny.framework.core.model.PagingParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends PagingParam {
    private String name;
    private String email;
    private String phone;
}
