package com.sk.config.smslogin;

import com.sk.config.filter.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * 短信验证配置
 *
 * @author zhangqiao
 * @since 2019/11/19 15:09
 */
@Configuration
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
    private UserDetailsService userDetailsService;

	@Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private PersistentTokenRepository persistentTokenRepository;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
		smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
//		smsCodeAuthenticationFilter.setAuthenticationSuccessHandler();
		smsCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		//设置记住我功能实现
		smsCodeAuthenticationFilter.setRememberMeServices(
		        new PersistentTokenBasedRememberMeServices("sk", userDetailsService, persistentTokenRepository));
        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
		SmsCodeAuthticationProvider provider = new SmsCodeAuthticationProvider();
		provider.setUserDetailsService(userDetailsService);
		http.authenticationProvider(provider)
				.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
