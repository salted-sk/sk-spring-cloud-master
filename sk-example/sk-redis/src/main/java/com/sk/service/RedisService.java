package com.sk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * redis测试类
 *
 * @author zhangqiao
 * @since 2020/2/25 9:44
 */
@Service
public class RedisService {

	@Autowired
	private RedisTemplate redisTemplate;

	public void test() {
		redisTemplate.afterPropertiesSet();
	}

}
