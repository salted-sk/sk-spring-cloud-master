package com.sk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 资源服务配置
 *
 * @author zhangqiao
 * @since 2019/11/6 16:13
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig  extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resource) throws Exception {
        resource.accessDeniedHandler(handleAccessDeniedForUser())
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .requestMatchers()
                    // 保险起见，防止被主过滤器链路拦截
                    .antMatchers("/**/*.json")
                    .and()
                .authorizeRequests()
                    .antMatchers("/**")
                    .access("#oauth2.hasScope('a')");//配置所有请求必须要有a的权限
    }

    private AccessDeniedHandler handleAccessDeniedForUser() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println("{\"code\":403,\"message\":\"小老弟，你好像没有权限访问呀！\",\"data\":\"\"}");
            response.getWriter().flush();
        };
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println("{\"code\":401,\"message\":\"小老弟，你的令牌好像不对啊！\",\"data\":\"\"}");
            response.getWriter().flush();
        };
    }

}