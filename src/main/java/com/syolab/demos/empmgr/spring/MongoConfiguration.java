package com.syolab.demos.empmgr.spring;

import com.mongodb.*;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    /*@Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private Integer port;
*/
    @Bean
    public Mongo mongo() throws Exception {
        MongoClientURI clientUri = new MongoClientURI(uri);
        return new MongoClient(clientUri);
    }


    @Override
    public String getDatabaseName() {
        return "employees";
    }

    @Override
    public String getMappingBasePackage() {
        return "com.syolab.demos.empmgr";
    }

    @Bean
    @Override
    public CustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new LongToDateTimeConverter());
        converterList.add(new StringToDateTimeConverter());
        converterList.add(new StringToDecimal128TimeConverter());
        return new CustomConversions(converterList);
    }

    @ReadingConverter
    static class LongToDateTimeConverter implements Converter<Long, Date> {
        @Override
        public Date convert(Long source) {
            if (source == null) {
                return null;
            }
            return new Date(source);
        }
    }

    @ReadingConverter
    static class StringToDateTimeConverter implements Converter<String, Date> {
        @Override
        public Date convert(String source) {
            if (source == null) {
                return null;
            }
            return new Date(source);
        }
    }
    @ReadingConverter
    static class StringToDecimal128TimeConverter implements Converter<String, Decimal128> {
        @Override
        public Decimal128 convert(String source) {
            if (source == null) {
                return null;
            }
            return new Decimal128(Integer.valueOf(source));
        }
    }
}
