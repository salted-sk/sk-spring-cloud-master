package com.sk.config;

import com.alibaba.fastjson.JSON;
import com.sk.common.config.po.CommonCode;
import com.sk.common.config.po.Result;
import com.sk.common.utils.EmptyUtils;
import com.sk.config.filter.JwtFilter;
import com.sk.config.filter.ValidateCodeFilter;
import com.sk.config.smslogin.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * 认证服务器配置
 *
 * @author zhangqiao
 * @since 2019/11/19 15:09
 */
@Order(1)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    //验证短信登陆配置
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /**
     * 配置自定义验证用户名、密码和授权的服务。
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 过滤静态资源
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置在登录之前校验此验证码过滤器
        http.addFilterBefore(new ValidateCodeFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加短信登陆
        http.apply(smsCodeAuthenticationSecurityConfig);
        http.formLogin()
                .loginPage("/login")
                //自定义登陆验证异常
                .failureHandler(authenticationFailureHandler())
                .successHandler(authenticationSuccessHandler())
                .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                // 失效时间
                .tokenValiditySeconds(3600)
                .userDetailsService(userDetailsService)
                .and()
            .authorizeRequests()
                .antMatchers("/login", "/code/**", "/mobile/login", "/actuator/health")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
            .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
            .csrf()
                .disable()
                .cors();
    }

    /**
     * 设置记住我功能
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository () {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        // 启动时自动创建表   如果数据库有该表，再设置为true，启动会报错
//        tokenRepositoryImpl.setCreateTableOnStartup(true);
        return tokenRepositoryImpl;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, authenticationException) -> {
            Result result = new Result();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json;charset=UTF-8");
            result.setCode(CommonCode.LOGIN_ERROR.code());
            result.setMessage(CommonCode.LOGIN_ERROR.message());
            response.getWriter().println(JSON.toJSON(result));
            response.getWriter().flush();
        };
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 使用BCrypt密码加密验证
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //登录成功处理
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String username = request.getParameter("username");
            if (EmptyUtils.isNotEmpty(username)){
                JwtFilter.userMap().put(username, request.getSession().getId());
            }
            //调用默认的successhandler
            new SavedRequestAwareAuthenticationSuccessHandler().onAuthenticationSuccess(request, response, authentication);
        };
    }

}
