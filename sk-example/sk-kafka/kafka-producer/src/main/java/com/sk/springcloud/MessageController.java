package com.sk.springcloud;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/12/24 14:16
 */
@RestController
@Slf4j
public class MessageController {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaMessageService kafkaMessageService;

    @GetMapping("")
    public String send() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a",1);
        jsonObject.put("b",2);
        jsonObject.put("c",3);

        //发送消息至exchange-data系统消费
        kafkaTemplate.send("pAsset", jsonObject.toString().getBytes());
        kafkaTemplate.send("pAsset", "ceshi".getBytes(), jsonObject.toString().getBytes());
        log.info("发送Kafka测试消息成功，memberId={},json={}","ceshi",jsonObject.toJSONString());
        return "OK";
    }

    @GetMapping("/stream/send")
    public boolean streamSend(@RequestParam String message) {
        // 获取 MessageChannel
        MessageChannel messageChannel = kafkaMessageService.pAsset();
        Map<String, Object> headers = new HashMap<>();
        headers.put("charset-encoding", "UTF-8");
        headers.put("content-type", MediaType.TEXT_PLAIN_VALUE);
        return messageChannel.send(new GenericMessage(message, headers));
    }

}
