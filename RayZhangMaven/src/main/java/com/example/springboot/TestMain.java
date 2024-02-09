package com.example.springboot;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.example.springboot.json.BaseInstanceEvent;
import com.example.springboot.json.ObjectMapperSingleton;
import com.example.springboot.json.ReqArrayInstanceEvent;

public class TestMain {
	public static void main(String[] args) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			System.out.println(sdf.parse("1212-12-12").toLocaleString());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(Locale.CANADA.getCountry()+"==========="+Locale.CANADA.getDisplayCountry());
//		BigDecimal value=BigDecimal.valueOf(-4);
//		System.out.println(BigDecimal.ZERO.compareTo(value));
//		String oldDescription="Business License No: 2222, Payment Date: 07/02/2022, Gross Receipts: $22.22, Period Paid: 07/21/2021\n";
//		String oldDescriptionPre =
//		          Optional.ofNullable(oldDescription).map(oDescription -> StringUtils.substringBefore(oDescription, "\n"))
//		            .orElse("");
//		System.out.println("==========="+oldDescription);
		LocalDateTime dateTime=LocalDateTime.now();
		System.out.println(dateTime.toInstant(ZoneOffset.UTC));
		}
	
}
