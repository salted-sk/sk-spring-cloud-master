package com.sk;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * --服务端-权限管理启动类
 *
 * @author zhangqiao
 * @since 2019/12/24 11:16
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)//配置使用Security权限
@SpringCloudApplication
@EnableFeignClients
public class ServerDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerDemoApplication.class,args);
    }
}
