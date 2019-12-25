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
@RabbitListener(queues = "FOURTH_QUEUE")
public class FourthConsumer {
    @RabbitHandler
    public void process(String msg){
        System.out.println("fourth queue received msg :" + msg);
    }

}
