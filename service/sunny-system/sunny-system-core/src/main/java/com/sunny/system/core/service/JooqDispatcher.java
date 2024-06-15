package com.sunny.system.core.service;

import com.esotericsoftware.kryo.util.ObjectMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.sunny.system.core.kind.SchemaTypeEnum;
import com.sunny.system.core.model.dto.FieldMetaDTO;
import com.sunny.system.core.model.dto.TableMetaDTO;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.jooq.impl.DSL.trueCondition;

@Service
public class JooqDispatcher {

    public final static PropertyNamingStrategies.NamingBase SNAKE_CASE_STRATEGY = new PropertyNamingStrategies.SnakeCaseStrategy();
    @Autowired
    ObjectMapper objectMapper;

    public TableMetaDTO getTableMeta(String tableCode) {
        URL resource;
        try {
            resource = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX + "jooq.json");
            return objectMapper.readValue(resource, TableMetaDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> list(String tableCode, Map<String, Object> query) {
        TableMetaDTO tableMeta = getTableMeta(tableCode);
        String tableName = String.join("_", tableMeta.getSchemaPrefix(), SchemaTypeEnum.BASE.name().toLowerCase());
        List<Field<?>> fields = tableMeta.getFields().stream()
                .filter(t -> t.getList() != null && t.getList())
                .map(t -> DSL.field(SNAKE_CASE_STRATEGY.translate(t.getName())).as(t.getName()))
                .collect(Collectors.toList());
        return DSL.select(fields).from(tableName).groupBy(fields).fetchMaps();
    }

    public List<Map<String, Object>> detail(String tableCode, Map<String, Object> query) {
        TableMetaDTO tableMeta = getTableMeta(tableCode);
        String baseTableName = String.join("_", tableMeta.getSchemaPrefix(), SchemaTypeEnum.BASE.name().toLowerCase());
        String detailTableName = String.join("_", tableMeta.getSchemaPrefix(), SchemaTypeEnum.DETAIL.name().toLowerCase());
        Table<?> baseTable = DSL.table(baseTableName).as("b");
        Table<?> detailTable = DSL.table(detailTableName).as("d");
        Condition condition = trueCondition();
        List<Field<?>> baseFields = tableMeta.getFields().stream()
                .filter(t -> SchemaTypeEnum.BASE.name().toLowerCase().equals(t.getSchemaSuffix()))
                .map(t -> DSL.field(String.join(".", "b", SNAKE_CASE_STRATEGY.translate(t.getName()))).as(t.getName()))
                .collect(Collectors.toList());
        List<Field<?>> detailFields = tableMeta.getFields().stream()
                .filter(t -> SchemaTypeEnum.DETAIL.name().toLowerCase().equals(t.getSchemaSuffix()))
                .map(t -> DSL.field(String.join(".", "b", SNAKE_CASE_STRATEGY.translate(t.getName()))).as(t.getName()))
                .collect(Collectors.toList());
        for (FieldMetaDTO t : tableMeta.getFields().stream().filter(t -> t.getList() != null && t.getList()).toList()) {
            Object value = query.get(t.getName());
            condition = condition.and(DSL.field(String.join(".", "b", SNAKE_CASE_STRATEGY.translate(t.getName())), Object.class).eq(value));
        }
        List<Field<?>> selectFields = Stream.concat(baseFields.stream(), detailFields.stream()).toList();
        return DSL
                .select(selectFields)
                .from(detailTable)
                .innerJoin(baseTable).on("b.id = d.base_id")
                .where(condition).fetchMaps();
    }

    public static void main(String[] args) {
    }

}
