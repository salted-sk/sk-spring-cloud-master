package com.sk.config;

import com.alibaba.fastjson.JSON;
import com.sk.common.config.po.CommonCode;
import com.sk.common.config.po.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 解决X-Frame-Options deny 造成的页面空白
 * 取消csrf（Cross-site request forgery跨站请求伪造）验证（不安全建议）
 * 前端在includ.html实现(2选1)
 *
 * @author zhangqiao
 * @since 2019/11/6 16:13
 */
@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/ruoyi/**", "/css/**", "/images/**", "/ajax/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .accessDeniedHandler(handleAccessDeniedForUser())
                .and()
            .formLogin()
                .and()
            .httpBasic()
                .and()
            .requestMatchers()
                .antMatchers("/**")
                .and()
            .authorizeRequests()
                .antMatchers("/login", "/actuator/health")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
            .headers()//配置解决X-Frame-Options deny 造成的页面空白
                .frameOptions()
                .disable();
        //配置取消跨域伪造验证（因页面实现所以这里注释了）
//        http.csrf().disable().cors();
    }

    /**
     * 自定义AccessDeniedHandler来处理Ajax请求。
     * @return
     */
    private AccessDeniedHandler handleAccessDeniedForUser() {
        return (HttpServletRequest request,
                HttpServletResponse response,
                AccessDeniedException accessDeniedException) -> {
            String requestedWithHeader = request.getHeader("X-Requested-With");
            Result result = new Result();
            if ("XMLHttpRequest".equals(requestedWithHeader)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println("{\"code\":403,\"message\":\"小老弟，系统好像异常了呢啊！\",\"data\":\"\"}");
                response.getWriter().flush();
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                result.setCode(CommonCode.UNAUTHORISE.code());
                result.setMessage(CommonCode.UNAUTHORISE.message());
                response.getWriter().println(JSON.toJSON(result));
                response.getWriter().flush();
            }
        };
    }



}
