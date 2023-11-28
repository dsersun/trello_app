package com.sersun.trello_app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Форматирование даты в формате "yyyy-MM-dd"
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat("yyyy-MM-dd")));
        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }
}
