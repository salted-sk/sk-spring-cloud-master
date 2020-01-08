package com.sk.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/1/7 17:21
 */
@Data
@ConfigurationProperties(prefix = "sk.spring.social")
public class SocialProperties {

    QqProperties qq = new QqProperties();

    VxProperties weixin = new VxProperties();

    WeiboProperties weibo = new WeiboProperties();

}
