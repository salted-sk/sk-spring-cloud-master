package com.sk.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/12/24 9:15
 */
@Component
public class MyProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(){
        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "sk.best", "a direct msg");
        rabbitTemplate.convertAndSend("TOPIC_EXCHANGE", "hf.sk.teacher", "a topic msg hf");
        rabbitTemplate.convertAndSend("TOPIC_EXCHANGE", "ah.sk.student", "a topic msg ah");
        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE", "", "a fanout msg");
    }

}
