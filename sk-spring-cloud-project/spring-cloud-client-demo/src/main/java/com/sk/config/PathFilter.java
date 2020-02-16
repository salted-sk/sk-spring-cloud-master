package com.sk.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 仅仅作为查看路径使用
 *
 * @author zhangqiao
 * @since 2019/12/16 13:07
 */
@Component
@WebFilter(filterName = "pathFiler", urlPatterns = {"/**"})
public class PathFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//		System.out.println(request.getRequestURI());

		filterChain.doFilter(request, response);
	}

}
