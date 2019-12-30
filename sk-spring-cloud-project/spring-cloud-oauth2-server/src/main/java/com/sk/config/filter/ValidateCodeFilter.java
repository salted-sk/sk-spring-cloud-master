package com.sk.config.filter;

import com.alibaba.fastjson.JSON;
import com.sk.common.config.po.CommonCode;
import com.sk.common.config.po.Result;
import com.sk.common.utils.EmptyUtils;
import com.sk.config.WebSecurityConfig;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码过滤器，可设置所有需要验证码之后才能继续请求执行
 *
 * @author zhangqiao
 * @since 2019/11/19 15:09
 * @see WebSecurityConfig
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    //需要通过验证码验证的uri
    private static Map<String, String> filterUris;

    static {
        filterUris = new HashMap();
        filterUris.put("/login", "POST");
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //访问请求，判断是否拦截此请求
		String uri = request.getServletPath();
        //校验验证码是否符合
        if (filterUris.containsKey(uri) && filterUris.get(uri).equalsIgnoreCase(request.getMethod())) {
            HttpSession session = request.getSession();
            String storageCode = (String) session.getAttribute(ValidateCodeUtil.RANDOM_CODE_KEY);
            //获取验证码
            String code = request.getParameter("validateCode");
            //判断验证码是否通过
            if (EmptyUtils.isEmpty(code) || EmptyUtils.isEmpty(storageCode) ||
                    !code.equalsIgnoreCase(storageCode)) {
                Result result = new Result();
                result.setCode(CommonCode.VALIDATE_ERROR.code());
                result.setMessage(CommonCode.VALIDATE_ERROR.message());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(JSON.toJSON(result));
                response.getWriter().flush();
                return;
            }
        }
		//验证通过后下一步
		filterChain.doFilter(request, response);
	}

}
