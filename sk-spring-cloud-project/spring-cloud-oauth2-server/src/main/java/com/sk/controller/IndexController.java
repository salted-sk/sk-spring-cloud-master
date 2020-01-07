package com.sk.controller;

import com.sk.common.utils.EmptyUtils;
import com.sk.config.LoginUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

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

    @GetMapping("/login")
    public String login(Principal principal, HttpServletResponse response) {
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
