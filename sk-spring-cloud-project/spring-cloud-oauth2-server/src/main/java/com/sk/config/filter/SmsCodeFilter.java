package com.sk.config.filter;

import com.sk.config.WebSecurityConfig;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 短信验证码过滤器，可设置所有需要验证短信码之后才能继续请求执行
 *
 * @author zhangqiao
 * @since 2019/11/19 15:09
 * @see WebSecurityConfig
 * @see ValidateCodeFilter 可参见此验证码
 */
public class SmsCodeFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //访问请求，判断是否拦截此请求
        request.getRequestURI();
        //访问类型post/get
        request.getMethod();
		//验证通过后下一步
		filterChain.doFilter(request, response);

	}
}
