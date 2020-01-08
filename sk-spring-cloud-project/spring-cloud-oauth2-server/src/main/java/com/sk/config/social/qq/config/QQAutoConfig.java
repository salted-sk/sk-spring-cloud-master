package com.sk.config.social.qq.config;

import com.sk.config.properties.SocialProperties;
import com.sk.config.social.SocialAutoConfigurerAdapter;
import com.sk.config.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * qq登陆配置
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
@Configuration
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SocialProperties socialProperties;

	@Override
    protected ConnectionFactory<?> createConnectionFactory() {
		return new QQConnectionFactory(
				socialProperties.getQq().getFilterProcessesUrl(),
				socialProperties.getQq().getClientId(),
				socialProperties.getQq().getClientSecret());
	}

}
