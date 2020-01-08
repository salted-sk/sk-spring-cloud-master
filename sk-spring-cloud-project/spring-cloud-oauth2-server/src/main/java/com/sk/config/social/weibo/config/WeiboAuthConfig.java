package com.sk.config.social.weibo.config;

import com.sk.config.properties.SocialProperties;
import com.sk.config.social.SocialAutoConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.weibo.connect.WeiboConnectionFactory;

@Configuration
public class WeiboAuthConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SocialProperties socialProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new WeiboConnectionFactory(
                socialProperties.getWeibo().getFilterProcessesUrl(),
                socialProperties.getWeibo().getClientId(),
                socialProperties.getWeibo().getClientSecret());
    }

    /**
     * /connect/qq POST请求,绑定微信返回connect/weixinConnected视图
     * /connect/qq DELETE请求,解绑返回connect/weixinConnect视图
     * @return
     */
//    @Bean({"connect/weiboConnect", "connect/weiboConnected"})
//    @ConditionalOnMissingBean(name = "weiboConnectedView")
//    public View qqConnectedView() {
//        return new SocialConnectView();
//    }
}
