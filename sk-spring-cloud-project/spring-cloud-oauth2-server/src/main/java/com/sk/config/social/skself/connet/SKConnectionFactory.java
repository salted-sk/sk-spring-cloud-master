package com.sk.config.social.skself.connet;

import com.sk.config.social.skself.api.SK;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * 登陆配置
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
public class SKConnectionFactory extends OAuth2ConnectionFactory<SK> {

	public SKConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new SKServiceProvider(appId, appSecret), new SKAdapter());
	}

}
