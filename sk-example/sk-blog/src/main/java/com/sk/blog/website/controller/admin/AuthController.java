package com.sk.blog.website.controller.admin;

import com.sk.blog.website.controller.BaseController;
import com.sk.blog.website.exception.TipException;
import com.sk.blog.website.service.ILogService;
import com.sk.blog.website.service.IUserService;
import com.sk.common.utils.EmptyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * 用户后台登录/登出
 * Created by BlueT on 2017/3/11.
 */
@Controller
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class AuthController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private IUserService usersService;

    @Resource
    private ILogService logService;

    @GetMapping(value = "/login")
    public String login(Principal principal, HttpServletResponse response) {
        if (EmptyUtils.isNotEmpty(principal)) {
            try {
                response.sendRedirect("/admin/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "login";
    }

//    @PostMapping(value = "login")
//    @ResponseBody
//    public RestResponseBo doLogin(@RequestParam String username,
//                                  @RequestParam String password,
//                                  @RequestParam(required = false) String remeber_me,
//                                  HttpServletRequest request,
//                                  HttpServletResponse response) {
//
//        Integer error_count = cache.get("login_error_count");
//        try {
//            UserVo user = usersService.login(username, password);
//            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, user);
//            if (StringUtils.isNotBlank(remeber_me)) {
//                TaleUtils.setCookie(response, user.getUid());
//            }
//            logService.insertLog(LogActions.LOGIN.getAction(), null, request.getRemoteAddr(), user.getUid());
//        } catch (Exception e) {
//            error_count = null == error_count ? 1 : error_count + 1;
//            if (error_count > 3) {
//                return RestResponseBo.fail("您输入密码已经错误超过3次，请10分钟后尝试");
//            }
//            cache.set("login_error_count", error_count, 10 * 60);
//            String msg = "登录失败";
//            if (e instanceof TipException) {
//                msg = e.getMessage();
//            } else {
//                LOGGER.error(msg, e);
//            }
//            return RestResponseBo.fail(msg);
//        }
//        return RestResponseBo.ok();
//    }

//    /**
//     * 注销
//     *
//     * @param session
//     * @param response
//     */
//    @RequestMapping("/logout")
//    public void logout(HttpSession session, HttpServletResponse response, HttpServletRequest request) {
//        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
//        Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, "");
//        cookie.setValue(null);
//        cookie.setMaxAge(0);// 立即销毁cookie
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        try {
//            response.sendRedirect("/admin/login");
//        } catch (IOException e) {
//            e.printStackTrace();
//            LOGGER.error("注销失败", e);
//        }
//    }

}
