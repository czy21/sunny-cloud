package com.sunny.framework.test.model.po;

import com.sunny.framework.core.model.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = true)
@Table("user")
@Data
public class UserPO extends BasePO<Long,String> {

    @Id
    private Long id;

    private String name;

    private Integer age;

    private String address;

    private String status;
}
