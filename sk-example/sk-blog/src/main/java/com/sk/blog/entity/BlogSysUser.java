package com.sk.blog.entity;

import com.sk.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_user")
@Getter
@Setter
public class BlogSysUser extends BaseEntity {
	/**
	 * 用户名称
	 */
	private String username;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 用户的邮箱
	 */
	private String email;
	private String phone;
	private String sex;

	/**
	 * 用户的主页
	 */
	@Column(name = "home_url")
	private String homeUrl;

	/**
	 * 用户显示的名称
	 */
	@Column(name = "screen_name")
	private String screenName;

	/**
	 * 用户注册时的GMT unix时间戳
	 */
	private Integer created;

	/**
	 * 最后活动时间
	 */
	private Integer activated;

	/**
	 * 上次登录最后活跃时间
	 */
	private Integer logged;

	/**
	 * 用户组
	 */
	@Column(name = "group_name")
	private String groupName;
}
