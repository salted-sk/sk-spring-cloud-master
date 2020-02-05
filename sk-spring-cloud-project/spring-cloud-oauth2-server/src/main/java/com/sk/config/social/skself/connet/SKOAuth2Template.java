package com.sk.config.social.skself.connet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 登陆配置
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
public class SKOAuth2Template extends OAuth2Template {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public SKOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}
	
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		Map<String, Object> responseMap = getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class);
		
		logger.info("获取accessToke的响应："+responseMap.get("access_token"));
		
		String accessToken = (String) responseMap.get("access_token");
		Integer expiresIn = (Integer) responseMap.get("expires_in");
		String refreshToken = (String) responseMap.get("refresh_token");
		
		return new AccessGrant(accessToken, null, refreshToken, expiresIn.longValue());
	}
	
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = super.createRestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}

}
