package com.sk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 多数据源启动类
 *
 * @author zhangqiao
 * @since 2019/12/29 12:18
 */
@SpringBootApplication
public class DataSourcesApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataSourcesApplication.class, args);
	}
}
