package com.sk.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展token信息
 * @author zhangqiao
 * @since 2019/11/19 15:09
 */
public class ExtTokenEnhancer implements TokenEnhancer {

	@Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		//扩展的token信息
		Map<String, Object> info = new HashMap<>();
		if (authentication.getPrincipal() instanceof LoginUser) {
			LoginUser loginUser = (LoginUser) authentication.getPrincipal();
			info.put("serverSession", loginUser.getServerSession());
			info.put("account", loginUser.getAccount());
			info.put("truename", loginUser.getTruename());
		}
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
