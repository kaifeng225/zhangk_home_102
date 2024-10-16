package com.example.springboot.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.springboot.freemarker.templateengine.TemplateProcessingException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileResourceValueService {
    private final List<String> approvedFileResourceValuePaths;
    private final FileContentService fileContentService;

    private List<Pattern> approvedFilePatterns;

    @PostConstruct
    public void init() {
        approvedFilePatterns = approvedFileResourceValuePaths.stream().map(StringUtils::trim).map(Pattern::compile)
          .collect(Collectors.toList());
    }

    public void validateFileResourceValues(Object target) {
        doWithFileResourceValueFields(target, (field, filePath) -> {
            if (!FileUtils.classPathResourceExists(filePath)) {
                throw new TemplateProcessingException(String.format("File not found: %s", filePath));
            }
        });
    }

    public void populateFileResourceValues(Object target) {
        doWithFileResourceValueFields(target, (field, filePath) -> ReflectionUtils.setField(field, target,
          fileContentService.getClassPathFileContent(filePath)));
    }

    private void doWithFileResourceValueFields(Object target, BiConsumer<Field, String> fc) {
        ReflectionUtils.doWithFields(target.getClass(), field -> {
            String filePath = validateAndGetFilePath(target, field);
            if (StringUtils.isEmpty(filePath)) {
                return;
            }
            fc.accept(field, filePath);
        }, field -> field.isAnnotationPresent(FileResourceValue.class));
    }

    private String validateAndGetFilePath(Object target, Field field) {
        Class<?> type = field.getType();
        if (!String.class.equals(type)) {
            log.error(String.format("Unsupported type for %s field, with %s annotation", field.getName(),
              FileResourceValue.class.getSimpleName()));
            throw new IllegalStateException(
              String.format("Unsupported type for %s annotation", FileResourceValue.class.getSimpleName()));
        }

        field.setAccessible(true);
        String filePath = StringUtils.trim((String) ReflectionUtils.getField(field, target));
        if (fileNotAllowed(filePath)) {
            throw new TemplateProcessingException(String.format("File not allowed to access: %s", filePath));
        }

        return filePath;
    }

    private boolean fileNotAllowed(String filePath) {
        return StringUtils.isNotEmpty(filePath)
          && approvedFilePatterns.stream().noneMatch(pattern -> pattern.matcher(filePath).matches());
    }
}
