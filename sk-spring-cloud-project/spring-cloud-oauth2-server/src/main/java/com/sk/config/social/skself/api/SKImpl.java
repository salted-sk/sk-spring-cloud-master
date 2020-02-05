package com.sk.config.social.skself.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * 获取信息
 *
 * @author zhangqiao
 * @since 2020/1/6 12:35
 */
public class SKImpl extends AbstractOAuth2ApiBinding implements SK {
	
	private static final String URL_GET_USERINFO = "http://oauth.chaseself.com/skblog/getUserInfo.json?oauth_consumer_key=%s";

	private String appId;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public SKImpl(String accessToken, String appId) {
		super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appId = appId;
	}
	
	@Override
	public SKUserInfo getUserInfo() {
		String url = String.format(URL_GET_USERINFO, appId);
		String result = getRestTemplate().getForObject(url, String.class);
		SKUserInfo userInfo;
		try {
			userInfo = objectMapper.readValue(result, SKUserInfo.class);
			return userInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取用户信息失败", e);
		}
	}

}
