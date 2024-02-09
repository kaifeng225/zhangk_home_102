package com.example.springboot.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

	private FileUtils() {
	}

	public static String readClassPathResourceAsString(String fileName) throws IOException {
		String result;

		try (InputStream reader = new ClassPathResource(fileName).getInputStream()) {
			result = StreamUtils.copyToString(reader, StandardCharsets.UTF_8);
		}
		return result;
	}

	public static Map<String, String> readProperties(String fileName) {
		Properties properties = new Properties();
		// Use ClassLoader tp load properties setting file as input stream
		InputStream in = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
		// use object properties to load input stream
		try {
			properties.load(in);
			// get key mapping value
			Set<Object> keySet = properties.keySet();
			return keySet.stream().map(key -> key.toString())
					.collect(Collectors.toMap(pkey -> pkey, properties::getProperty));
		} catch (Exception e) {
			log.error("Property file read failed! name={}", fileName);
			return Maps.newHashMap();
		}
	}

	public static String loadTemplate(String templateDir) {
    	 try {
             File templateFile = ResourceUtils.getFile(FileUtils.class.getResource(templateDir));
             return org.apache.commons.io.FileUtils.readFileToString(templateFile, StandardCharsets.UTF_8);
         } catch (IOException e) {
             e.printStackTrace();
             return null;
         }
    }
	
    public static boolean classPathResourceExists(String fileName) {
        return new ClassPathResource(fileName).exists();
    }

	public static void main(String[] args) {
		Map<String, String> proMap = readProperties("");
		System.out.print(proMap);

	}
}
