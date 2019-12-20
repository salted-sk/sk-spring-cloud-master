package com.sk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import tk.mybatis.spring.annotation.MapperScan;

/**
 *  springcloud配置服务器启动类
 *
 * @author zhangqiao
 * @since 2019/9/29 15:18
 */
@SpringBootApplication
@EnableConfigServer
@MapperScan(basePackages = "com.sk.dao")
@EnableDiscoveryClient
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
