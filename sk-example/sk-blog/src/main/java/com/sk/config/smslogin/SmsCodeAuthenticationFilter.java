package com.sk.config.smslogin;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信验证登录
 *
 * @author zhangqiao
 * @since 2019/11/19 15:09
 */
public class SmsCodeAuthenticationFilter  extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";

	private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;

	private boolean postOnly = true;

	public SmsCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher("/admin/mobile/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (this.postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		} else {
			String mobile = this.obtainMobile(request);
			if (mobile == null) {
				mobile = "";
			}
			mobile = mobile.trim();
			SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
			this.setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
		}
	}

	protected String obtainMobile(HttpServletRequest request) {
		return request.getParameter(this.mobileParameter);
	}

	protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	public void setMobileParameter(String mobileParameter) {
		Assert.hasText(mobileParameter, "手机号不能为空");
		this.mobileParameter = mobileParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getMobileParameter() {
		return this.mobileParameter;
	}

}

