package com.example.springboot.patternMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMather {

	public static void main(String[] args) {
//		test1();
		testEmail(" ACTIVE@GMAIL.COM ");
	}
	
	
	private static void test1() {
		String workflowPermissionIdentifier="23455-e6dfd223-e5ed-4030-b0e4-06ea16e95512";
		Pattern pattern = Pattern.compile("(?<type>-?\\d+)-(?<value>.+)");
        Matcher matcher = pattern.matcher(workflowPermissionIdentifier);
        if(!matcher.matches()) {
        	System.out.println("no matches");
        	return ;
        }
        System.out.println(matcher.group("type")+"===="+ matcher.group("value"));
	}
	
	private static void testEmail(String email) {
		String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()) {
        	System.out.println("no matches");
        	return ;
        }
		System.out.println("good matches");
	}

}
