package com.example.springboot.redis;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {

	@Autowired
	private RedisService redisService;

	@RequestMapping("/setRedis")
	public String setRedis(@RequestParam("redismessage") UUID uuid) {
		log.info("access to set redis");
		redisService.setRedisMessage(uuid);
		return "success";
	}

	@RequestMapping("/getRedis")
	public String getRedis() {
		String message=redisService.getRedisMessage();
		log.info("access to get redis, message={}",message);
		return message;
	}
	
	@RequestMapping("/storeWithSortedsets")
	public String storeWithSortedsets(@RequestParam("redismessage") UUID uuid) {
		redisService.increaseClientScore(uuid.toString());
		log.info("access to storeWithSortedsets, uuid={}",uuid.toString());
		return uuid.toString();
	}
}
