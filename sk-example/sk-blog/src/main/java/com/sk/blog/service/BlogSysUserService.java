package com.sk.blog.service;

import com.sk.blog.entity.BlogSysUser;
import com.sk.blog.dao.BlogSysUserDao;
import com.sk.common.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class BlogSysUserService  extends BaseService<BlogSysUser, BlogSysUserDao> {
	public BlogSysUser getUserByUsername(String username) {
		return selectOne(() -> {
			BlogSysUser user = new BlogSysUser();
			user.setUsername(username);
			return user;
		});
	}

	public BlogSysUser getUserByEmail(String email) {
		return selectOne(() -> {
			BlogSysUser user = new BlogSysUser();
			user.setEmail(email);
			return user;
		});
	}

	public BlogSysUser getUserByMobile(String mobile) {
		return selectOne(() -> {
			BlogSysUser user = new BlogSysUser();
			user.setPhone(mobile);
			return user;
		});
	}
}
