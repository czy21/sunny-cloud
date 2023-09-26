package com.sunny.system.core.model.query;

import com.sunny.framework.core.model.PagingParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictValueQuery extends PagingParam {

    private Long dictId;
}