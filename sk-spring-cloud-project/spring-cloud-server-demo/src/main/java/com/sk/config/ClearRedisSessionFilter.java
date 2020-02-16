package com.sk.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 1.判断当前用户是否登录，如未登录时，清除session信息，防止session共享时单点登录引发的无限重定向问题
 * 2.当用户登录时，根据需求是否重新定义当前用户权限
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
		boolean updateAuthority = false;
		//用户未登录；登录请求；不是第三方引发的跳转->则清除相关认证信息
		if (requiresClearRedisSession(request)) {
			if (request.getParameter("code") == null) {
				request.getSession().removeAttribute(CLEAR_ATTRIBUTE);
			} else {
				updateAuthority = true;
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
		if (updateAuthority) {
			//是否需要重新定义当前用户权限
			//reloadUserAuthority(request.getSession());
		}
	}


	/**
	 * 重新加载用户的权限
	 *
	 * @param session
	 */
	private void reloadUserAuthority(HttpSession session) {
		//重新定义权限
		List<GrantedAuthority> authorityList =
				AuthorityUtils.createAuthorityList(new String[]{"重新定义权限集合！"});
		SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		Authentication authentication = securityContext.getAuthentication();
		Object principal = authentication.getPrincipal();
		// 重新定义生成一个token，因为Authentication中的权限是不可变的.
		UsernamePasswordAuthenticationToken result =
				new UsernamePasswordAuthenticationToken(principal, authentication.getCredentials(),
						authorityList);
		result.setDetails(authentication.getDetails());
		securityContext.setAuthentication(result);
	}

	private boolean requiresClearRedisSession(HttpServletRequest request) {
		return this.clearRedisSessionRequestMatcher.matches(request);
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.clearRedisSessionRequestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
	}
}
