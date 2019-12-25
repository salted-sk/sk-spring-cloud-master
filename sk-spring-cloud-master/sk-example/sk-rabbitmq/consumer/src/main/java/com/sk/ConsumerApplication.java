package com.sk;

import com.sk.springcloud.SimpleMessageReceiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/12/24 9:13
 */
@SpringBootApplication
@EnableBinding(SimpleMessageReceiver.class) // 激活并引入 SimpleMessageReceiver
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
