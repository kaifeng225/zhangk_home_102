package com.example.springboot.json;

import java.time.ZonedDateTime;

public class Persion {

	String name;
	
	ZonedDateTime endDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ZonedDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(ZonedDateTime endDate) {
		this.endDate = endDate;
	}
	
}
