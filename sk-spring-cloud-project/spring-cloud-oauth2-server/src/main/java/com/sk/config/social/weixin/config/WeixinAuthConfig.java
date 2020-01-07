//package com.sk.config.social.weixin.config;
//
//import com.sk.config.social.SocialAutoConfigurerAdapter;
//import com.sk.config.social.weixin.connect.WeixinConnectionFactory;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.social.connect.ConnectionFactory;
//import org.springframework.web.servlet.View;
//import sun.security.util.SecurityConstants;
//
//
////import cn.merryyou.logback.social.SocialAutoConfigurerAdapter;
//
///**
// *
// * * @author zhangqiao
// * @since 2020/1/7 13:17
// */
//@Configuration
//public class WeixinAuthConfig extends SocialAutoConfigurerAdapter {
//
//    @Override
//    protected ConnectionFactory<?> createConnectionFactory() {
//        return new WeixinConnectionFactory(DEFAULT_SOCIAL_WEIXIN_PROVIDER_ID, SecurityConstants.DEFAULT_SOCIAL_WEIXIN_APP_ID,
//                SecurityConstants.DEFAULT_SOCIAL_WEIXIN_APP_SECRET);
//    }
//
////    /**
////     * /connect/weixin POST请求,绑定微信返回connect/weixinConnected视图
////     * /connect/weixin DELETE请求,解绑返回connect/weixinConnect视图
////     * @return
////     */
////    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
////    @ConditionalOnMissingBean(name = "weixinConnectedView")
////    public View weixinConnectedView() {
////        return new SocialConnectView();
////    }
//
//}
