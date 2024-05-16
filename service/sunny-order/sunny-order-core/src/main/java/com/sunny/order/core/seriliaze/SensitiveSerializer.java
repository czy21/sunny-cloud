package com.sunny.order.core.seriliaze;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sunny.auth.common.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
@JsonComponent
public class SensitiveSerializer extends JsonSerializer<String> {

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value);
    }
}
