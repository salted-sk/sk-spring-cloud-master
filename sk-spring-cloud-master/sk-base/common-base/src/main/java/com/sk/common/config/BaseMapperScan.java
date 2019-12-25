package com.sk.common.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 基础mapper扫描
 *
 * @author zhangqiao
 * @since 2019/12/18 8:59
 */
@Configuration
@MapperScan(basePackages = "com.sk.common.base.dao")
public class BaseMapperScan {
}
