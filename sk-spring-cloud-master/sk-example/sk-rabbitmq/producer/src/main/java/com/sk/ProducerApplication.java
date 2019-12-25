package com.sk;

import com.sk.springcloud.SimpleMessageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/12/24 9:14
 */
@SpringBootApplication
@EnableBinding(SimpleMessageService.class) // 激活并引入 SimpleMessageService
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class,args);
    }
}
