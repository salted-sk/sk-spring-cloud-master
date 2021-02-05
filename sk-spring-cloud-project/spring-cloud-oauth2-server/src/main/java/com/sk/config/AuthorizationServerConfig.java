package com.sk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权服务器配置
 *
 * @author zhangqiao
 * @since 2019/11/19 15:09
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .tokenKeyAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //若无，refresh_token会有UserDetailsService is required错误
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager)
//                .accessTokenConverter(jwtAccessTokenConverter())//扩展jwttoken时取消
                //多节点下可使用redis持久化token
//                .tokenStore(jwtTokenStore());
                .tokenStore(redisTokenStore());
                //扩展jwttoken
                TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
                List<TokenEnhancer> enhancerList = new ArrayList<>();
                enhancerList.add(tokenEnhancer());
                enhancerList.add(jwtAccessTokenConverter());//扩展jwttoken时取消
                tokenEnhancerChain.setTokenEnhancers(enhancerList);
//                endpoints.tokenEnhancer(tokenEnhancerChain);

                //使用jdbc保存code以达到多节点认证
                //.authorizationCodeServices(authorizationCodeServices());
    }

    @Bean
    public TokenStore jwtTokenStore() {
        JwtTokenStore tokenStore =  new JwtTokenStore(jwtAccessTokenConverter());
        return tokenStore;
    }

    /**
     * 多节点情况下使用jdbc作为存储code机制达到多节点认证
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("sk");   // JWT key-value
        return jwtAccessTokenConverter;
    }

    //扩展token信息
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new TokenEnhancerExtension();
    }

    //使用reids来持久化token
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore redisTokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix("taiyi:oauth2.0:");
        tokenStore.setAuthenticationKeyGenerator(new SkAuthenticationKeyGenerator(tokenStore));
        return tokenStore;
    }
}
