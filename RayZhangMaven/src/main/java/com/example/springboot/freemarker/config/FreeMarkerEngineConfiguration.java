package com.example.springboot.freemarker.config;

import static freemarker.core.DefaultTruncateBuiltinAlgorithm.STANDARD_M_TERMINATOR;

import java.time.LocalDate;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.example.springboot.json.ObjectMapperSingleton;
import com.example.springboot.xml.XmlMapperSingleton;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.google.common.collect.Maps;

import freemarker.cache.CacheStorage;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.NullCacheStorage;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.DefaultTruncateBuiltinAlgorithm;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.context.annotation.Configuration
public class FreeMarkerEngineConfiguration {

    @Bean
    public freemarker.template.Configuration freeMarkerConfiguration() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_20);
        TemplateLoader[] loaders = new TemplateLoader[] {classTemplateLoader(), templateLoader()};
        MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(loaders);
        cfg.setTemplateLoader(multiTemplateLoader);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);

        // Wrap unchecked exceptions thrown during template processing into TemplateException-s:
        cfg.setWrapUncheckedExceptions(true);

        // Do not fall back to higher scopes when reading a null loop variable:
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setTruncateBuiltinAlgorithm(new DefaultTruncateBuiltinAlgorithm(
          "", STANDARD_M_TERMINATOR, true));

        cfg.setCacheStorage(cacheStorage());
        setUpSharedVariableAndDirectives(cfg);
        return cfg;
    }

    /**
     * Set up shared variable and functions
     * For example, if we register a shared variable called 'role', we can access the variable through
     * <pre>@role</pre> Be aware that you need to use '@' rather than '#' used for properties
     */
    private void setUpSharedVariableAndDirectives(Configuration configuration) {
        Map<String, Object> sharedVars = Maps.newHashMap();
        sharedVars.put("quotationMark", "\"");
        sharedVars.put("randomAmount", (TemplateMethodModelEx) input -> String.format("%.2f", new Random().nextDouble() * 100));
        sharedVars.put("randomOrderId", (TemplateMethodModelEx) input -> UUID.randomUUID().toString());
        sharedVars.put("randomCreateDt", (TemplateMethodModelEx) input -> LocalDate.now());
        sharedVars.put("convertToInt", (TemplateMethodModelEx) input -> {
            if (input.size() != 1) {
                throw new IllegalArgumentException("only 1 argument allowed");
            }
            Object intValue = input.get(0);
            if (intValue == null || StringUtils.isEmpty(intValue.toString())) {
                return "";
            }
            return String.valueOf(NumberUtils.toInt(intValue.toString(), Integer.MAX_VALUE));
        });
        sharedVars.put("convertToLong", (TemplateMethodModelEx) input -> {
            if (input.size() != 1) {
                throw new IllegalArgumentException("only 1 argument allowed");
            }
            Object longValue = input.get(0);
            if (longValue == null || StringUtils.isEmpty(longValue.toString())) {
                return "";
            }
            return String.valueOf(NumberUtils.toLong(longValue.toString(), Long.MAX_VALUE));
        });
        sharedVars.put("parseXML", (TemplateMethodModelEx) input -> {
            if (input.size() != 1) {
                throw new IllegalArgumentException("only 1 argument allowed");
            }
            return XmlMapperSingleton.readValue(input.get(0).toString());
        });
        sharedVars.put("parseJson", (TemplateMethodModelEx) input -> {
            if (input.size() != 1) {
                throw new IllegalArgumentException("only 1 argument allowed");
            }
            return ObjectMapperSingleton.readObjectValueMap(input.get(0).toString());
        });
        sharedVars.put("toQuotedJson", (TemplateMethodModelEx) input -> {
            if (input.size() != 1) {
                throw new IllegalArgumentException("only 1 argument allowed");
            }
            Object value = input.get(0);
            if (value instanceof SimpleHash) {
                Map map = ((SimpleHash) value).toMap();
                return new String(JsonStringEncoder.getInstance().quoteAsString(ObjectMapperSingleton.writeValueAsString(map)));
            }
            return new String(JsonStringEncoder.getInstance().quoteAsString(ObjectMapperSingleton.writeValueAsString(value)));
        });

        sharedVars.forEach((key, value) -> {
            try {
                configuration.setSharedVariable(key, value);
            } catch (TemplateModelException e) {
                log.error("Failed to register shared variable to vendor template engine: [{}]:[{}]", key, value);
            }
        });
    }

    @Bean
    CacheStorage cacheStorage() {
        return new NullCacheStorage();
    }

    /**
     * This is supposed to do the initialization of the original template string
     * (not the Template object) when the system boots up.
     * But since we wants to load the template from database instantly, we are not going to load
     * the templates here.
     * If we wants to load the templates from very beginning, consider it here.
     */
    @Bean
    TemplateLoader templateLoader() {
        return new StringTemplateLoader();
    }

    @Bean
    TemplateLoader classTemplateLoader() {
        // config to use absolute path
        return new ClassTemplateLoader(this.getClass(), "/");
    }
}
