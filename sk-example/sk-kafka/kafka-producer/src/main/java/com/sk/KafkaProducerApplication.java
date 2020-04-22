package com.sk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import com.sk.springcloud.KafkaMessageService;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/12/24 9:14
 */
@SpringBootApplication
@EnableBinding(KafkaMessageService.class) // 激活并引入 SimpleMessageService
public class KafkaProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class,args);
    }
}
