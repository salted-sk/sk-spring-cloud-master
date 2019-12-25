package com.sk.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/12/23 17:19
 */
@Component
@RabbitListener(queues = "THIRD_QUEUE")
public class SecondConsumer {
    @RabbitHandler
    public void process(String msg){
        System.out.println("second queue received msg :" + msg);
    }

}
