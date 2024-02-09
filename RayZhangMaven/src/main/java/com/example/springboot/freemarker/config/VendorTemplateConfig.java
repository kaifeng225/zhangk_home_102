package com.example.springboot.freemarker.config;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.springboot.freemarker.templateengine.BadTemplateException;
import com.example.springboot.freemarker.templateengine.KeyExtractor;
import com.example.springboot.freemarker.templateengine.TemplateMaker;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import freemarker.core.ParseException;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author joe tang on 2/10/21 3:37 PM
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class VendorTemplateConfig {
    private final freemarker.template.Configuration freeMarkerConfiguration;

    @Bean
    Cache<String, Template> vendorTemplateCache() {
        return Caffeine.newBuilder()
          .softValues()
          .expireAfterAccess(30, TimeUnit.MINUTES)
          .build();
    }

    @Bean
    KeyExtractor<String, String> templateNameExtractor() {
        return DigestUtils::md5Hex;
    }

    @Bean
    TemplateMaker vendorTemplateMaker(KeyExtractor<String, String> templateNameExtractor) {
        return templateStr -> {
            String templateName = templateNameExtractor.extractKey(templateStr);
            //it creates template instance or throws exception, null value is not possible for now
            //and we can by pass null value problem by using optional if needed
            Template newTemplate;
            try {
                newTemplate =
                  new Template(templateName, new StringReader(templateStr), freeMarkerConfiguration,
                    freeMarkerConfiguration.getDefaultEncoding());
            } catch (ParseException e) {
                log.debug("Creating template error: {}; {} Template String: {}",
                  e.getMessage(), System.lineSeparator(), templateStr);
                throw new BadTemplateException("Template Not Valid, " + e.getMessage(), templateStr, e);
            } catch (IOException e) {
                throw new BadTemplateException("Unexpected template failure:", templateStr, e);
            }
            return newTemplate;
        };
    }
}
