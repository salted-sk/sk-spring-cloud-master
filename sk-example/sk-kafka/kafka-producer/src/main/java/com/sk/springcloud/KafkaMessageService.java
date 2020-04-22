package com.sk.springcloud;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaMessageService {

    @Output("pAsset")
    MessageChannel pAsset();

}
