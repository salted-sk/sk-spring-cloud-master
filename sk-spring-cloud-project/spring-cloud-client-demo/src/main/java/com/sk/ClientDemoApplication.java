package com.sk;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * --客户端-角色管理启动类
 *
 * @author zhangqiao
 * @since 2019/12/24 11:15
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)//配置使用Security权限
@SpringCloudApplication
@EnableFeignClients
@EnableHystrix
public class ClientDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientDemoApplication.class,args);
    }
}
