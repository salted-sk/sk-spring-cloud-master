package com.sk.common.base.entity;

import com.sk.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 系统用户实体
 *
 * @author zhangqiao
 * @since 2019-11-11 09:25:07
 */
@Table(name = "sys_user")
@Data
public class SysUser extends BaseEntity<Integer> {

    /**
	 * 登陆账号
     */
    @Column(name = "account")
    private String account;

    /**
	 * 登陆密码    
     */
    @Column(name = "password")
    private String password;

    /**
	 * 手机号    
     */
    @Column(name = "phone")
    private String phone;

    /**
	 * 性别 0女 1男    
     */
    @Column(name = "sex")
    private String sex;

    /**
	 * 真实姓名    
     */
    @Column(name = "true_name")
    private String trueName;
}