package com.sk.config.social.weixin.config;

import com.sk.config.properties.SocialProperties;
import com.sk.config.social.SocialAutoConfigurerAdapter;
import com.sk.config.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * 微信登陆配置
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
@Configuration
public class WeixinAuthConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SocialProperties socialProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new WeixinConnectionFactory(
                socialProperties.getWeixin().getFilterProcessesUrl(),
                socialProperties.getWeixin().getClientId(),
                socialProperties.getWeixin().getClientSecret());
    }

//    /**
//     * /connect/weixin POST请求,绑定微信返回connect/weixinConnected视图
//     * /connect/weixin DELETE请求,解绑返回connect/weixinConnect视图
//     * @return
//     */
//    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
//    @ConditionalOnMissingBean(name = "weixinConnectedView")
//    public View weixinConnectedView() {
//        return new SocialConnectView();
//    }

}
