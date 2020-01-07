package com.sk.config.social.qq.config;

import com.sk.config.social.SocialAutoConfigurerAdapter;
import com.sk.config.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionFactory;

/**
 * qq登陆配置
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
@Configuration
@EnableSocial
@Order(2)
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Value("${spring.social.qq.client-id}")
    private String qqClientId;

    @Value("${spring.social.qq.client-secret}")
    private String qqClientSecret;

    @Value("${spring.social.qq.filterProcessesUrl}")
    private String providerId;

	@Override
    protected ConnectionFactory<?> createConnectionFactory() {
		return new QQConnectionFactory(providerId, qqClientId, qqClientSecret);
	}

}
