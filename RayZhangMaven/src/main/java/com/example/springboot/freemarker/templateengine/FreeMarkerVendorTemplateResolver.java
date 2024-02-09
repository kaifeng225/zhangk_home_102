package com.example.springboot.freemarker.templateengine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Cache;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FreeMarkerVendorTemplateResolver implements VendorTemplateResolver {
    private final Cache<String, Template> vendorTemplateCache;
    private final KeyExtractor<String, String> templateNameExtractor;
    private final TemplateMaker vendorTemplateMaker;

    @Override
    public String resolveTemplate(String templateStr, Object properties) {
        String templateName = templateNameExtractor.extractKey(templateStr);
        Template template = vendorTemplateCache.get(templateName, v -> vendorTemplateMaker.makeTemplate(templateStr));
        StringWriter stringWriter = new StringWriter();

        try {
            Objects.requireNonNull(template).process(properties, stringWriter);
        } catch (TemplateException | IOException e) {
            log.debug("Processing template error: {}  template str: {}, template properties: {}",
              e.getMessage(), templateStr, properties);
            throw new TemplateProcessingException(
              "Processing template error :" + e.getMessage() + "Template string: " + System.lineSeparator(), templateStr,
              properties, e);
        }

        return stringWriter.toString();
    }

}
