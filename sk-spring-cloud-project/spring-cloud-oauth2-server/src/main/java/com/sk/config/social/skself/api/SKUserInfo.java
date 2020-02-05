package com.sk.config.social.skself.api;

import lombok.Data;

/**
 * SK用户信息
 *
 * @author zhangqiao
 * @since 2020/2/6 12:35
 */
@Data
public class SKUserInfo {

	private String username;
	private String phone;
	private String sex;
	private String imgUrl;
	private String screenname;
	private String providerUserId;

}
