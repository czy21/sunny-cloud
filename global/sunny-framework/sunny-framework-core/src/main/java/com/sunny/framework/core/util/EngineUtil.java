package com.sunny.framework.core.util;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;

import java.util.Map;

public class EngineUtil {

    public static <T> T getValue(Map<String, Object> object, String expression, Class<T> targetType) {
        try (Context context = Context.newBuilder("js").allowHostAccess(HostAccess.ALL).allowHostClassLookup(className -> true).build()) {
            return context.eval("js", "(function getValue(obj, expression){ return Function(\"obj\", \"return \" + expression)(obj) })").execute(object, expression).as(targetType);
        }
    }
}
