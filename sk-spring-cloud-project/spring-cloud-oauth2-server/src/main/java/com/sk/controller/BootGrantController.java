package com.sk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义授权页面
 *
 * @author zhangqiao
 * @since 2019/11/19 16:26
 */
@Controller
// 必须配置
@SessionAttributes("authorizationRequest")
public class BootGrantController {

	@Value("${server.servlet.context-path}")
	private String contextPaht;

	/**
	 * 自定义授权页面
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/oauth/confirm_access")
	public String authorize(Model modelpage, Map<String, Object> model, HttpServletRequest request) {
		AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
		modelpage.addAttribute("clientId", authorizationRequest.getClientId());
		Map<String, String> scopes = (Map<String, String>) (model.containsKey("scopes") ?
				model.get("scopes") : request.getAttribute("scopes"));
		List<String> scopeList = new ArrayList<>();
		if (scopes != null) {
			scopeList.addAll(scopes.keySet());
		}
		model.put("scopeList", scopeList);
		model.put("contextPaht", contextPaht);
		return "grant";
	}

}
