package com.sk.controller;

import com.sk.common.base.entity.SysUser;
import com.sk.common.base.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 三方登陆控制器
 *
 * @author zhangqiao
 * @since 2020/1/7 12:40
 */
@Controller
@RequestMapping("/social")
public class SocialController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SysUserService userService;

    /**
     * 注册界面
     *
     * @param map
     * @return
     */
    @GetMapping(value = "/register")
    public String socialRegister(Model model, HttpServletRequest request, Map<String, Object> map) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        model.addAttribute("providerId", connection.getKey().getProviderId());
        model.addAttribute("providerUserId", connection.getKey().getProviderUserId());
        model.addAttribute("nickname", connection.getDisplayName());
        model.addAttribute("headImg", connection.getImageUrl());
        return "register";
    }

    /**
     * 用户注册
     *
     * @param user
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/user/register")
    public String register(SysUser user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // TODO
//        String account = user.getAccount();
//        SysUser result = userService.getUserByUsername(account);
//        if (result == null) {
//            //注册用户
//            userService.saveAndUpdate(user);
//        }
//        //将业务系统的用户与社交用户绑定
//        providerSignInUtils.doPostSignUp(account, new ServletWebRequest(request));
        //跳转到index
        return "redirect:/";
    }
}
