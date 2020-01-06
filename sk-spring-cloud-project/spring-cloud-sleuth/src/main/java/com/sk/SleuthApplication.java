package com.sk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.internal.EnableZipkinServer;

/**
 * sleuth启动类
 *
 * @author zhangqiao
 * @date 2020/1/6
 */
@SpringBootApplication
@EnableZipkinServer
public class SleuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(SleuthApplication.class, args);
	}
}
