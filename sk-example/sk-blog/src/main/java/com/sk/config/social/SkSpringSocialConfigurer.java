package com.sk.config.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.stereotype.Component;

/**
 * 社交登录配类
 *
 * @author zhangqiao
 * @since 2020/1/7 15:09
 */
@Component
public class SkSpringSocialConfigurer extends SpringSocialConfigurer {

//    @Value("${spring.social.filterProcessesUrl}")
//	private String filterProcessesUrl;

    /**
     * 配置
     *
     * @param object
     * @param <T>
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
		//配置三方登陆的拦截url默认为auth
//		filter.setFilterProcessesUrl(filterProcessesUrl);
        //设置第三方注册页面默认跳转到/signin
        //需要SocialConfig不设置SocialConfig
        filter.setSignupUrl("/social/register");
		return (T) filter;
	}

}
