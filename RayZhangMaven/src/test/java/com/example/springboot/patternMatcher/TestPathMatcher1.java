package com.example.springboot.patternMatcher;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import com.google.common.collect.Maps;

public class TestPathMatcher1 {
	
	@Test
	public void test1() {
		String path="/agencies/{agencyGuid}/settings/finance";
		PathPattern pathPattern = PathPatternParser.defaultInstance.parse(path);
		String requestPath="/agencies/e6dfd223-e5ed-4030-b0e4-06ea16e95512/settings/finance";
		System.out.println(pathPattern.matches(PathContainer.parsePath(requestPath)));
		
	}

}
