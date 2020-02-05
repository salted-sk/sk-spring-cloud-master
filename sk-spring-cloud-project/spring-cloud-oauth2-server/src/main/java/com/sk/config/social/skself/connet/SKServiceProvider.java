package com.sk.config.social.skself.connet;

import com.sk.config.social.skself.api.SK;
import com.sk.config.social.skself.api.SKImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 授权信息配置
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
public class SKServiceProvider extends AbstractOAuth2ServiceProvider<SK> {

	private String appId;
	
	private static final String URL_AUTHORIZE = "http://oauth.chaseself.com/oauth/authorize";
	
	private static final String URL_ACCESS_TOKEN = "http://oauth.chaseself.com/oauth/token";
	
	public SKServiceProvider(String appId, String appSecret) {
		super(new SKOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = appId;
	}
	
	@Override
	public SK getApi(String accessToken) {
		return new SKImpl(accessToken, appId);
	}

}
