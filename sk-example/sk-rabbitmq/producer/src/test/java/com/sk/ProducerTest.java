package com.sk;

import com.sk.producer.MyProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/12/24 9:19
 */
@SpringBootTest
public class ProducerTest {

    @Autowired
    MyProducer producer;

    @Test
    public void contextLoads(){
        producer.send();
    }
}
