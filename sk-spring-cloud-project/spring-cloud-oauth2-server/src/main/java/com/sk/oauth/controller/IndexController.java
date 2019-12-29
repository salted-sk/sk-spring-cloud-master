package com.sk.oauth.controller;

import com.sk.common.utils.EmptyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/11/19 16:26
 */
@Controller
public class IndexController {

    @GetMapping("/login")
    public String login(Principal principal, HttpServletResponse response) {
        if (EmptyUtils.isNotEmpty(principal)) {
            try {
                response.sendRedirect("/");
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
        model.addAttribute("username", principal.getName());
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
