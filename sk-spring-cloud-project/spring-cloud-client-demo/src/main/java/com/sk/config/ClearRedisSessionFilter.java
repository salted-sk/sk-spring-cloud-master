package com.sk.config;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

/**
 * 判断当前用户是否登录，如未登录时，清除session信息，防止session共享时单点登录引发的无限重定向问题
 *
 * @author zhangqiao
 * @since 2020/2/15 15:09
 * @see UserSecurityConfig
 */
public class ClearRedisSessionFilter extends GenericFilterBean {

	private static final String CLEAR_ATTRIBUTE = "scopedTarget.oauth2ClientContext";

	private RequestMatcher clearRedisSessionRequestMatcher;

	public ClearRedisSessionFilter() {
		this.setFilterProcessesUrl("/login");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		System.out.println(request.getServletPath() + ":" + request.getMethod());
		Principal principal = request.getUserPrincipal();
		//用户未登录；登录请求；不是第三方引发的跳转->则清除相关认证信息
		if (principal == null && requiresClearRedisSession(request)
			&& request.getParameter("code") == null) {
			request.getSession().removeAttribute(CLEAR_ATTRIBUTE);
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	private boolean requiresClearRedisSession(HttpServletRequest request) {
		return this.clearRedisSessionRequestMatcher.matches(request);
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.clearRedisSessionRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
	}
}
