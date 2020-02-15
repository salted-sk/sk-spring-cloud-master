package com.sk.config;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;

/**
 * 判断当前用户是否登录，如未登录时，清除session信息，防止session共享时单点登录引发的无限重定向问题
 *
 * @author zhangqiao
 * @since 2020/2/15 15:09
 * @see UserSecurityConfig
 */
public class ClearRedisSessionFilter extends GenericFilterBean {

	private RequestMatcher clearRedisSessionRequestMatcher;

	public ClearRedisSessionFilter() {
		this.setFilterProcessesUrl("/login");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		System.out.println(request.getServletPath() + ":" + request.getMethod());
		Principal principal = request.getUserPrincipal();
		if (principal == null && requiresClearRedisSession(request, response)
				&& request.getParameter("code") == null) {
			Enumeration em = request.getSession().getAttributeNames();
			while(em.hasMoreElements()){
				request.getSession().removeAttribute(em.nextElement().toString());
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	private boolean requiresClearRedisSession(HttpServletRequest request, HttpServletResponse response) {
		return this.clearRedisSessionRequestMatcher.matches(request);
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.clearRedisSessionRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
	}
}
