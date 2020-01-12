package com.sk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/12/13 14:12
 */
@Configuration
@MapperScan(basePackages = {"com.sk.dao"})
@RefreshScope
public class DynamicDataSource {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DataSource dataSource =
                DataSourceBuilder.create()
                        .url(datasourceUrl)
                        .username(username)
                        .password(password)
                        .build();
        return dataSource;
    }
}
