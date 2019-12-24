package com.sk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * --客户端启动类
 *
 * @author zhangqiao
 * @since 2019/12/24 11:15
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ClientDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientDemoApplication.class,args);
    }
}
