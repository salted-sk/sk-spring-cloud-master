package com.sk.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 配置缓存策略
 *
 * @author zhangqiao
 * @since 2020/1/3 9:01
 */
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

	/**
	 * 定义缓存数据， key生成策略的bean
	 * 包名+类名+方法名+所有参数
	 */
	@Bean
	public KeyGenerator wiselyKeyGenerator(){
		return (targer, method, params) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(targer.getClass().getName()+"-");
			sb.append(method.getName()+"-");
			for (Object param : params) {
				sb.append(param.toString());
			}
			return sb.toString();
		};
	}

	/***
	 * 缓存管理配置
	 * @return
	 */
	@Bean
	public RedisCacheConfiguration redisCacheConfiguration(){
		return RedisCacheConfiguration
				.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(5))   // 设置5分钟key的失效时间。
				.serializeKeysWith(
						RedisSerializationContext
								.SerializationPair
								.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(
						RedisSerializationContext
								.SerializationPair
								.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	}

}
