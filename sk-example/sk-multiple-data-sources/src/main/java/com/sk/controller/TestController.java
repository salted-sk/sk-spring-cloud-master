package com.sk.controller;

import com.sk.common.base.entity.SysUser;
import com.sk.config.datasource.DataSource;
import com.sk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多数据源测试
 *
 * @author zhangqiao
 * @since 2019/12/29 12:18
 */
@RestController
public class TestController {

	@Autowired
	private UserService userService;

	/**
	 * 获取不同数据源的用户列表
	 *
	 * @return
	 */
	@GetMapping("/list")
	public Map<String, Object> getUsers() {
		Map<String, Object> userMaps = new HashMap<>();
		List<SysUser> defaultUsers = userService.selectList();
		List<SysUser> masterUsers = userService.masterUsers();
		List<SysUser> slaveUsers = userService.slaveUsers();
		userMaps.put("默认用户", defaultUsers);
		userMaps.put("主数据源用户", masterUsers);
		userMaps.put("从数据源用户", slaveUsers);
		return userMaps;
	}

}
