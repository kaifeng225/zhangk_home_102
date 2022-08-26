package com.example.springboot.file;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class FileUtilsTest {

	@Test
	public void testLoadTemplate() {
		String templatePath="/template/vendor/class/quote/response/single/respTemplate.ftl";
		log.info("=============="+FileUtils.loadTemplate(templatePath));
	}
}
