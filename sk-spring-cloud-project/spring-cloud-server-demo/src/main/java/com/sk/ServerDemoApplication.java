package com.sk;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * --服务端启动类
 *
 * @author zhangqiao
 * @since 2019/12/24 11:16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServerDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerDemoApplication.class,args);
    }
}
