package com.sk.config.social.skself.config;

import com.sk.config.properties.SocialProperties;
import com.sk.config.social.SocialAutoConfigurerAdapter;
import com.sk.config.social.skself.connet.SKConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * 登陆配置
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
@Configuration
public class SKAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SocialProperties socialProperties;

	@Override
    protected ConnectionFactory<?> createConnectionFactory() {
		return new SKConnectionFactory(
				socialProperties.getSkself().getFilterProcessesUrl(),
				socialProperties.getSkself().getClientId(),
				socialProperties.getSkself().getClientSecret());
	}

}
