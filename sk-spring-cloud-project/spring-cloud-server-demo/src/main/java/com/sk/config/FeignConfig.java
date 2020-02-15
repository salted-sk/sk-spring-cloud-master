package com.sk.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * feign调用传递token
 *
 * @author zhangqiao
 * @since 2019/11/1 9:52
 */
@Configuration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if(token == null || "".equals(token)) {
                token = "Bearer " + ((DefaultOAuth2ClientContext)request.getSession().getAttribute("scopedTarget.oauth2ClientContext")).getAccessToken().getValue();
            }
            System.out.println(token);
            requestTemplate.header(HttpHeaders.AUTHORIZATION, token);
        }
    }

}
