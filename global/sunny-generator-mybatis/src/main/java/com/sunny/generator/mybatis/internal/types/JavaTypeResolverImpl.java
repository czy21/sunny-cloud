package com.sunny.generator.mybatis.internal.types;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;

public class JavaTypeResolverImpl extends JavaTypeResolverDefaultImpl {

    @Override
    protected FullyQualifiedJavaType overrideDefaultType(IntrospectedColumn column, FullyQualifiedJavaType defaultType) {
        if (column.getJdbcType() == Types.TINYINT || column.getJdbcType() == Types.SMALLINT) {
            return new FullyQualifiedJavaType(Integer.class.getName());
        }
        return super.overrideDefaultType(column, defaultType);
    }
}