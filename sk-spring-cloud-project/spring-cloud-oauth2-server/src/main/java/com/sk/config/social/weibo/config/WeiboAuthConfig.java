//package com.sk.config.social.weibo.config;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter;
//import org.springframework.social.connect.ConnectionFactory;
//import org.springframework.social.weibo.connect.WeiboConnectionFactory;
//import org.springframework.web.servlet.View;
//import sun.security.util.SecurityConstants;
//
////import cn.merryyou.logback.social.SocialAutoConfigurerAdapter;
//
//
//@Configuration
//public class WeiboAuthConfig extends SocialAutoConfigurerAdapter {
//
//    @Override
//    protected ConnectionFactory<?> createConnectionFactory() {
//        return new WeiboConnectionFactory(SecurityConstants.DEFAULT_SOCIAL_WEIBO_APP_ID, SecurityConstants.DEFAULT_SOCIAL_WEIBO_APP_SECRET);
//    }
//
//    /**
//     * /connect/qq POST请求,绑定微信返回connect/weixinConnected视图
//     * /connect/qq DELETE请求,解绑返回connect/weixinConnect视图
//     * @return
//     */
//    @Bean({"connect/weiboConnect", "connect/weiboConnected"})
//    @ConditionalOnMissingBean(name = "weiboConnectedView")
//    public View qqConnectedView() {
//        return new SocialConnectView();
//    }
//}
