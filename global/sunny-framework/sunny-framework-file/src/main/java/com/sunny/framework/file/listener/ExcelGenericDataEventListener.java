package com.sunny.framework.file.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.framework.file.excel.BaseExcelModel;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExcelGenericDataEventListener<T> extends AnalysisEventListener<T> {
    private final Logger logger = LoggerFactory.getLogger(ExcelGenericDataEventListener.class);
    private final Validator validator;
    private final List<T> rows = new ArrayList<>();
    private final Map<Integer, List<String>> error = new TreeMap<>();
    private final Context<T> processContext = new Context<>();
    private final ObjectMapper objectMapper;
    private final StringRedisTemplate redisTemplate;
    private int batch = 200;
    private int total = 0;
    private AtomicInteger successTotal = new AtomicInteger(0);
    private Consumer<Context<T>> processConsumer;
    private String token;
    private Map<Integer, String> indexNameMap = new HashMap<>();

    private int expireMinutes = 30;
    public final static String EXCEL_STORAGE_KEY_PREDIX = "generic:excel:import";
    public final static Function<String, String> DATA_KEY_PREFIX_FUNC = t -> String.join(":", EXCEL_STORAGE_KEY_PREDIX, t, "data");
    public final static Function<String, String> ERROR_KEY_PREFIX_FUNC = t -> String.join(":", EXCEL_STORAGE_KEY_PREDIX, t, "error");

    public ExcelGenericDataEventListener(Consumer<Context<T>> processConsumer,
                                         ObjectMapper objectMapper,
                                         StringRedisTemplate redisTemplate,
                                         Validator validator) {
        this.processConsumer = processConsumer;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
        this.validator = validator;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public Consumer<Context<T>> getProcessConsumer() {
        return processConsumer;
    }

    public void setProcessConsumer(Consumer<Context<T>> processConsumer) {
        this.processConsumer = processConsumer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpireMinutes() {
        return expireMinutes;
    }

    public void setExpireMinutes(int expireMinutes) {
        this.expireMinutes = expireMinutes;
    }

    public Map<Integer, String> getIndexNameMap() {
        return indexNameMap;
    }

    public void setIndexNameMap(Map<Integer, String> indexNameMap) {
        this.indexNameMap = indexNameMap;
    }

    public int getTotal() {
        return total;
    }

    public AtomicInteger getSuccessTotal() {
        return successTotal;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        total++;
        int rowIndex = analysisContext.readRowHolder().getRowIndex() + 1;
        if (rowIndex > total) {
            error.put(rowIndex, new ArrayList<>());
            if (t instanceof BaseExcelModel) {
                ((BaseExcelModel) t).setRowIndex(rowIndex);
            } else {
                Map<Integer, Object> tMap = (Map<Integer, Object>) t;
                t = (T) indexNameMap.entrySet().stream().collect(LinkedHashMap::new, (m, n) -> Optional.ofNullable(tMap.get(n.getKey())).ifPresent(c -> m.put(n.getValue(), c)), Map::putAll);
                ((LinkedHashMap<String, Object>) t).put("rowIndex", rowIndex);
            }
            rows.add(t);
            if (rows.size() >= batch) {
                processRows();
                rows.clear();
                error.clear();
            }
        } else {
            total--;
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        processRows();
    }

    private void processRows() {
        rows.stream().findFirst().ifPresent(r -> {
            if (r instanceof BaseExcelModel) {
                rows.forEach(t -> validator.validate(t).forEach(e -> error.get(((BaseExcelModel) t).getRowIndex()).add(e.getMessage())));
            }
        });
        processContext.setRows(rows);
        processContext.setError(error);
        processContext.setTotal(total);
        processContext.setSuccessTotal(successTotal);
        processConsumer.accept(processContext);
        String errorKey = ERROR_KEY_PREFIX_FUNC.apply(token);
        for (Map.Entry<Integer, List<String>> m : error.entrySet().stream().filter(t -> CollectionUtils.isNotEmpty(t.getValue())).toList()) {
            try {
                redisTemplate.opsForHash().put(errorKey, String.valueOf(m.getKey()), objectMapper.writeValueAsString(m.getValue()));
            } catch (JsonProcessingException e) {
                logger.error("excel存储校验失败", e);
            }
        }
        redisTemplate.expire(errorKey, expireMinutes, TimeUnit.MINUTES);
        String dataKey = DATA_KEY_PREFIX_FUNC.apply(token);
        for (T t : rows) {
            try {
                redisTemplate.opsForList().rightPush(dataKey, objectMapper.writeValueAsString(t));
            } catch (JsonProcessingException e) {
                logger.error("excel存储数据失败", e);
            }
        }
        redisTemplate.expire(dataKey, expireMinutes, TimeUnit.MINUTES);
    }

    /**
     * 数据处理上下文
     *
     * @param <T>
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Context<T> {
        private List<T> rows;
        private Map<Integer, List<String>> error;
        private int total;
        private AtomicInteger successTotal;
    }
}
