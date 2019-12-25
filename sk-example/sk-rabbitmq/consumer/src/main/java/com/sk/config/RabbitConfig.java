package com.sk.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/12/23 16:32
 */
@Configuration
public class RabbitConfig {

    //定义三个交换机
    @Bean//直连类型交换机
    public DirectExchange directExchange(){
        return new DirectExchange("DIRECT_EXCHANGE");
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("TOPIC_EXCHANGE");
    }
    @Bean//广播类型
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("FANOUT_EXCHANGE");
    }

    //定义4个队列
    @Bean
    public Queue firstQueue(){
        return new Queue("FIRST_QUEUE");
    }
    @Bean
    public Queue secondQueue(){
        return new Queue("SECOND_QUEUE");
    }
    @Bean
    public Queue thirdQueue(){
        return new Queue("THIRD_QUEUE");
    }
    @Bean
    public Queue fourthQueue(){
        return new Queue("FOURTH_QUEUE");
    }

    //定义4个绑定关系
    @Bean
    public Binding bindFirst(@Qualifier("firstQueue") Queue queue,
                             @Qualifier("directExchange") DirectExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("sk.best");
    }
    @Bean
    public Binding bindSecond(@Qualifier("secondQueue") Queue queue,
                              @Qualifier("topicExchange") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("*.sk.*");
    }
    @Bean
    public Binding bindThird(@Qualifier("thirdQueue") Queue queue,
                             @Qualifier("fanoutExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindFourth(@Qualifier("fourthQueue") Queue queue,
                             @Qualifier("fanoutExchange") FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

}
