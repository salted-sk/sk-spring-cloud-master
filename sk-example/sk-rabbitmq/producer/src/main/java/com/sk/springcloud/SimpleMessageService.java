package com.sk.springcloud;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SimpleMessageService {

    @Output("sk2019") // Channel name
    MessageChannel sk2019(); //  destination = test2019

    @Output("test007")
    MessageChannel testChannel(); //  destination = test007

    @Output("test-http")
    MessageChannel http(); //  destination = http
}
