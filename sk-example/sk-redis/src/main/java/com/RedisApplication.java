package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *暂定redis启动类
 *
 * @author zhangqiao
 * @since 2019/12/24 14:16
 */
@SpringBootApplication
@EnableCaching
public class RedisApplication {
	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

	@Bean
	public RedisTemplate redisTemplate() {
		return new RedisTemplate();
	}
}
