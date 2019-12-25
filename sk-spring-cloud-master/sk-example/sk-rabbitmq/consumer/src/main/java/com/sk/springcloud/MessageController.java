package com.sk.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * 接收消息(同一类型的处理方式同时处理消息，所以需要对消息做幂等性处理)
 *
 * @author zhangqiao
 * @since 2019/12/24 14:16
 */
@RestController
public class MessageController {

    @Autowired
    private SimpleMessageReceiver simpleMessageReceiver;

    @PostConstruct
    public void init() {  // 接口编程
        // 获取 SubscribableChannel
        SubscribableChannel subscribableChannel = simpleMessageReceiver.sk2019();
        subscribableChannel.subscribe(message -> {
            MessageHeaders headers = message.getHeaders();
            String encoding = (String) headers.get("charset-encoding");
            String text = (String) headers.get("content-type");
            byte[] content = (byte[]) message.getPayload();
            try {
                System.out.println("接受到消息：" + new String(content, encoding));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }

    @StreamListener("sk2019")  // Spring Cloud Stream 注解驱动
    public void onMessage(byte[] data) {
        System.out.println("接受到消息(byte[]): " + new String(data));
    }

    @StreamListener("sk2019")  // Spring Cloud Stream 注解驱动
    public void onMessage(String data) {
        System.out.println("接受到消息(String) : " + data);
    }

    @ServiceActivator(inputChannel = "sk2019") // Spring Integration 注解驱动
    public void onServiceActivator(String data) {
        System.out.println("接受到消息(String) : " + data);
    }

    @StreamListener("test007")  // Spring Cloud Stream 注解驱动
    public void onMessageFromRocketMQ(byte[] data) {
        System.out.println("RocketMQ - onMessage(byte[]): " + new String(data));
    }

    @StreamListener("test-http")  // Spring Cloud Stream 注解驱动
    public void onMessageFromHttp(byte[] data) {
        System.out.println("HTTP - onMessage(byte[]): " + new String(data));
    }

}
