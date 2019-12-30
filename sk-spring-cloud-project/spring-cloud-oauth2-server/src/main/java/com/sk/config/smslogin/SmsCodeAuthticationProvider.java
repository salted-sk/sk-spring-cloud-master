package com.sk.config.smslogin;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 验证token
 * TODO 我觉得应该继承这个AbstractUserDetailsAuthenticationProvider类 实现里面的验证 但是我不想写了怎么滴
 *
 * @author zhangqiao
 * @since 2019/11/19 15:09
 */
public class SmsCodeAuthticationProvider implements AuthenticationProvider {

	private UserDetailsService userDetailsService;

    @Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
		UserDetails userDetails = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
		if (userDetails == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		SmsCodeAuthenticationToken tokenResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
		tokenResult.setDetails(authenticationToken.getDetails());
		return tokenResult;
	}

    @Override
	public boolean supports(Class<?> aClass) {
		return SmsCodeAuthenticationToken.class.isAssignableFrom(aClass);
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
