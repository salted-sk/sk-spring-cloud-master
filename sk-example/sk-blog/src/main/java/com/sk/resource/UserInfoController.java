package com.sk.resource;

import com.sk.blog.entity.BlogSysUser;
import com.sk.blog.service.BlogSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/2/5 21:02
 */
@RestController
@RequestMapping("/skblog")
public class UserInfoController {
	@Autowired
	private BlogSysUserService userService;

	@GetMapping("/getUserInfo.json")
	@ResponseBody
	public Map getListJson(Principal principal) {
		String username = principal.getName();
		BlogSysUser user = userService.getUserByUsername(username);
		Map<String, Object> map = new HashMap<>();
		map.put("username", user.getUsername());
		map.put("screenname", user.getScreenName());
		map.put("phone", user.getPhone());
		map.put("sex", user.getSex());
		map.put("imgUrl", user.getHomeUrl());
		map.put("providerUserId", "sk-oauth->" + username);
		return map;
	}
}
