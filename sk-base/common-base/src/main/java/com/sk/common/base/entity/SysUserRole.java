package com.sk.common.base.entity;

import com.sk.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 实体
 *
 * @author zhangqiao
 * @since 2019-11-11 09:25:07
 */
@Getter
@Setter
@Table(name = "sys_user_role")
public class SysUserRole extends BaseEntity {

    /**
	 * 用户id    
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
	 * 角色id    
     */
    @Column(name = "role_id")
    private Integer roleId;

}