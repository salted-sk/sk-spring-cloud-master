package com.sk.controller;

import com.sk.common.utils.EmptyUtils;
import com.sk.config.LoginUser;
import com.sk.config.properties.SocialProperties;
import com.sk.my.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Collection;

/**
 * 基本信息
 *
 * @author zhangqiao
 * @since 2019/11/19 16:26
 */
@Controller
public class IndexController {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private SocialProperties socialProperties;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/login")
    public String login(Principal principal, HttpServletResponse response) {
        Collection<OAuth2AccessToken> tokensByClientId = applicationContext.getBean(RedisTokenStore.class).findTokensByClientId("client-demo");
        if (EmptyUtils.isNotEmpty(principal)) {
            try {
                response.sendRedirect(contextPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "login";
    }

    /**
     * 跳转到首页。
     */
    @GetMapping("/")
    public String index(Principal principal, Model model) {
        if (principal != null && ((AbstractAuthenticationToken) principal).getPrincipal() instanceof LoginUser){
            LoginUser user = (LoginUser) ((AbstractAuthenticationToken) principal).getPrincipal();
            model.addAttribute("username", user.getTruename());
            model.addAttribute("imageUrl", user.getImageUrl());
        }
        return "index";
    }

    /**
     * 获取当前登录用户。
     */
    @GetMapping("/principal")
    @ResponseBody
    public Principal getUserRidAuthority(Principal principal){
        return principal;
    }

}
