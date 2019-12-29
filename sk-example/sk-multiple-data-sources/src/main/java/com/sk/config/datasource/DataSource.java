package com.sk.config.datasource;

import java.lang.annotation.*;

/**
 * 动态配置数据源注解
 *
 * @author zhangqiao
 * @since 2019/12/29 12:18
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

	/**
	 * 数据源
	 */
	String value() default "master";

}
