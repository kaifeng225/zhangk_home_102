package com.example.springboot.freemaker.vendor;

import java.util.Map;

import org.assertj.core.util.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springboot.Application;
import com.example.springboot.file.FileUtils;
import com.example.springboot.freemarker.templateengine.FreeMarkerVendorTemplateResolver;
import com.example.springboot.json.ObjectMapperSingleton;

import junit.framework.TestCase;
import lombok.extern.log4j.Log4j2;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class TestAuthorize {

	@Autowired
	private FreeMarkerVendorTemplateResolver freeMarkerVendorTemplateResolver;
	private String templateAuthorizeReqPath = "/template/vendor/class/authorize/response/respTemplate.ftl";

	@BeforeEach
	public void init() {
		log.info("============init====={}", freeMarkerVendorTemplateResolver);
	}

	@Test
	public void testAuthorizeRequest() {
		String tempStr = FileUtils.loadTemplate(templateAuthorizeReqPath);
		String token = "authenticationToken";
		Map<String, String> response = Maps.newHashMap("authenticationToken", token);
		Map<String, Map<String, String>> params = Maps.newHashMap("response", response);
		String res = freeMarkerVendorTemplateResolver.resolveTemplate(tempStr, params);
		log.info("=========resultStr:{}", res);
		Map<String, String> result = ObjectMapperSingleton.readStringValueMap(res);
		TestCase.assertEquals(result.get("authenticationToken"), token);
	}
}
