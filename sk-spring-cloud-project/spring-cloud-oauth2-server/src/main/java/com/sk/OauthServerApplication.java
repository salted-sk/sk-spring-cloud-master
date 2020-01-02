package com.sk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 认证-单点服务器启动类
 *
 * @author zhangqiao
 * @since 2019/12/26 22:35
 */
@SpringBootApplication
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)//使用redis管理session并设置超时时间（配置文件设置无效）
public class OauthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthServerApplication.class,args);
    }
}
