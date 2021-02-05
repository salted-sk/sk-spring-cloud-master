package com.sk.config;

import com.alibaba.fastjson.JSON;
import com.sk.common.config.po.CommonCode;
import com.sk.common.config.po.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

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

        //使用redis进行session共享时，登录前清除session信息，防止session共享时单点登录引发的无限重定向问题
        http.addFilterBefore(new ClearRedisSessionFilter(), LogoutFilter.class);

        http.headers()
                .frameOptions()
                .disable()
                .and()
            .requestMatchers()
                .antMatchers("/**")
                .and()
            .authorizeRequests()
                .antMatchers("/actuator/health", "/login")
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
            Result result = new Result();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            result.setCode(CommonCode.UNAUTHORISE.code());
            result.setMessage("小老弟，你是没注册还是密码错了啊！");
            response.getWriter().println(JSON.toJSON(result));
            response.getWriter().flush();
        });
    }

}
