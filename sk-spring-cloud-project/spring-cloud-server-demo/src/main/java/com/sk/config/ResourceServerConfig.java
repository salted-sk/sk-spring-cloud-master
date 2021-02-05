package com.sk.config;

import com.alibaba.fastjson.JSON;
import com.sk.common.config.po.CommonCode;
import com.sk.common.config.po.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
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
        resource.tokenStore(redisTokenStore());
        resource.accessDeniedHandler(handleAccessDeniedForUser())
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    //使用reids来持久化token
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore redisTokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        return tokenStore;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .requestMatchers()
                    // 保险起见，防止被主过滤器链路拦截
                    .antMatchers("/**/*.json")
                    .and()
                .authorizeRequests()
                    .anyRequest()
                    .authenticated();
    }

    private AccessDeniedHandler handleAccessDeniedForUser() {
        return (request, response, accessDeniedException) -> {
            Result result = new Result();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            result.setCode(CommonCode.UNAUTHORISE.code());
            result.setMessage(CommonCode.UNAUTHORISE.message());
            response.getWriter().println(JSON.toJSON(result));
            response.getWriter().flush();
        };
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) -> {
            Result result = new Result();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            result.setCode(CommonCode.UNAUTHORISE.code());
            result.setMessage("小老弟，你的令牌好像不对啊！");
            response.getWriter().println(JSON.toJSON(result));
            response.getWriter().flush();
        };
    }

}