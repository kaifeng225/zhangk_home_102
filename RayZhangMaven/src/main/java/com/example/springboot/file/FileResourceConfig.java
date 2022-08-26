package com.example.springboot.file;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Robyn Liu
 */
@Configuration
public class FileResourceConfig {
    /**
     * The cache for file content.
     * key: the file path
     * value: the file content
     * @return
     */
    @Bean
    public Cache<String, String> fileContentCache() {
        return Caffeine.newBuilder().softValues().build();
    }

    @Bean
    public List<String> approvedFileResourceValuePaths() {
        return Lists.newArrayList("^/template/.*");
    }
}
