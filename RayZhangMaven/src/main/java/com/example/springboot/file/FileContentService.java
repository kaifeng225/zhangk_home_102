package com.example.springboot.file;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.springboot.freemarker.templateengine.TemplateProcessingException;
import com.github.benmanes.caffeine.cache.Cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Robyn Liu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileContentService {
	private final Cache<String, String> fileContentCache;

	public String getClassPathFileContent(String filePath) {
		return fileContentCache.get(filePath, this::readClassPathFileContent);
	}

	String readClassPathFileContent(String filePath) {
		try {
			return FileUtils.readClassPathResourceAsString(filePath);
		} catch (IOException e) {
			log.error("File not found: {}", filePath);
			throw new TemplateProcessingException("File not found", e);
		}
	}
}
