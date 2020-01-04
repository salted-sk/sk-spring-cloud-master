package com.sk.config.filter;

import com.sk.common.utils.EmptyUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * token拦截器
 *
 * @author zhangqiao
 * @since 2019/12/16 13:07
 */
@Component
@WebFilter(filterName = "tokenFiler", urlPatterns = {"/**"})
public class JwtFilter extends OncePerRequestFilter {

	private static Map<String, String> userMap = new ConcurrentHashMap<>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Principal principal = request.getUserPrincipal();
		if (principal != null){
			String account = principal.getName();
			String sessionId = userMap.get(account);
			HttpSession session = request.getSession();
			if (EmptyUtils.isNotEmpty(sessionId) && !session.getId().equals(sessionId)) {
				response.sendRedirect("logout");
			}
		}
		filterChain.doFilter(request, response);
	}

	public static Map<String, String> userMap() {
		return userMap;
	}

	private static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzgxNDUyNTIsInVzZXJfbmFtZSI6InpoYW5nc2FuIiwiYXV0aG9yaXRpZXMiOlsiUk9MRTpTQVZFIiwiUEVSTUlTU0lPTjpRVUVSWSIsIlBFUk1JU1NJT046U0FWRSIsIlBFUk1JU1NJT046REVMRVRFIiwiUk9MRTpRVUVSWSIsIlJPTEU6REVMRVRFIl0sImp0aSI6IjdlMWQ2YjU3LWY1YzQtNDk0Yy1iNDJkLWU4OTRjYjE3MTU3NSIsImNsaWVudF9pZCI6ImNsaWVudC1kZW1vIiwic2NvcGUiOlsiYWxsIiwicm9sZSJdfQ.q2Djk4s29eLKFHXwd_aqPs7T5sB2OiZ7CDmJNs5bb8I";
	public static void main(String[] args) {
		try {
			Jwt jwt = JwtHelper.decode(token);
			String claimsStr = jwt.getClaims();
			Map<String, Object> claims = JsonParserFactory.create().parseMap(claimsStr);
			if (claims.containsKey("exp") && claims.get("exp") instanceof Integer) {
				Integer intValue = (Integer)claims.get("exp");
				claims.put("exp", new Long((long)intValue));
			}
		} catch (Exception var6) {
			throw new InvalidTokenException("Cannot convert access token to JSON", var6);
		}
	}
}
