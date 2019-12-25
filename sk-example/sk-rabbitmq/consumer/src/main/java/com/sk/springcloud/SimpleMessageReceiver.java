package com.sk.springcloud;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SimpleMessageReceiver {

    @Input("sk2019")
    SubscribableChannel sk2019();

    @Input("test007")
    SubscribableChannel testChannel();

    @Input("test-http")
    SubscribableChannel httpChannel();
}
