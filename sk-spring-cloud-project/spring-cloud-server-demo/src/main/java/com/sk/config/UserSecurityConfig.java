package com.sk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * 单点登陆认证配置
 *
 * @author zhangqiao
 * @since 2019/11/6 16:13
 */
@Configuration
@EnableOAuth2Sso
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.sso.login.url}")
    private String ssoLoginUrl;

    @Value("${app.sso.logout.url}")
    private String ssoLogoutUrl;

    /**
     * 过滤静态资源
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .accessDeniedHandler(handleAccessDeniedForUser())
                .and()
            .headers()
                .frameOptions()
                .disable()
                .and()
            .requestMatchers()
                .antMatchers("/**")
                .and()
            .authorizeRequests()
                .antMatchers("/actuator/health")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
            .formLogin()
                .failureHandler(authenticationFailureHandler())
                .and()
            .logout()
                .logoutSuccessUrl(ssoLogoutUrl)
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
            .csrf()
                .disable()
                .cors();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return ((request, response, authenticationException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println("{\"code\":403,\"message\":\"小老弟，你是没注册还是密码错了啊！\",\"data\":\"\"}");
            response.getWriter().flush();
        });
    }

    /**
     * 自定义AccessDeniedHandler来处理Ajax请求。
     * @return
     */
    private AccessDeniedHandler handleAccessDeniedForUser() {
        return (request, response, accessDeniedException) -> {
            String requestedWithHeader = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(requestedWithHeader)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getOutputStream().write(objectMapper.writeValueAsBytes(accessDeniedException.getMessage()));
            } else {
                response.sendRedirect(ssoLoginUrl);
            }
        };
    }



}
