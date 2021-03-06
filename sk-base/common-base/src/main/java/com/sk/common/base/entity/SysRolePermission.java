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
@Table(name = "sys_role_permission")
public class SysRolePermission extends BaseEntity {

    /**
	 * 角色id    
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
	 * 权限id    
     */
    @Column(name = "permission_id")
    private Integer permissionId;
                    
}