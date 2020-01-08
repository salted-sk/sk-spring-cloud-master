package com.sk.config.social;

import com.sk.blog.entity.BlogSysUser;
import com.sk.blog.service.BlogSysUserService;
import com.sk.common.base.entity.SysUser;
import com.sk.common.base.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * 因自动给当前三方登陆用户配置一个账号，此时配置注册跳转无效
 *
 * @author zhangqiao
 * @since 2020/1/7 13:17
 */
@Component
public class ConnectionSignUpImpl implements ConnectionSignUp {

    @Autowired
    private BlogSysUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    /* (non-Javadoc)
	 * @see org.springframework.social.connect.ConnectionSignUp#execute(org.springframework.social.connect.Connection)
	 */
	@Override
	public String execute(Connection<?> connection) {
		//根据社交用户信息默认创建用户并返回用户唯一标识
//		return connection.getDisplayName();
		return autoSignup(connection);
	}

    /**
     * 自动帮助用户注册
     *
     * @param connection
     * @return
     */
	private String autoSignup(Connection<?> connection) {
        BlogSysUser user;
	    String account = connection.getKey().toString();
	    user = userService.getUserByUsername(account);
	    if (user == null) {
	        user = new BlogSysUser();
	        user.setUsername(account);
	        user.setScreenName(connection.getDisplayName());
	        user.setHomeUrl(connection.getImageUrl());
	        //默认给定当前用户密码
	        user.setPassword(passwordEncoder.encode("123456"));
	        userService.saveAndUpdate(user);
        }
        return account;
    }

}
