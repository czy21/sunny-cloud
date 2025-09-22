package com.sunny.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

public class RenameModelPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {

        String baseRecordType = introspectedTable.getBaseRecordType();
        String pkg = baseRecordType.substring(0, baseRecordType.lastIndexOf('.') + 1);
        String shortName = baseRecordType.substring(baseRecordType.lastIndexOf('.') + 1);

        if (!shortName.endsWith("PO")) {
            shortName = shortName + "PO";
        }

        introspectedTable.setBaseRecordType(pkg + shortName);

    }
}