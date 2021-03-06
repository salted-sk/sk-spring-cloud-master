package com.sk.common.base.entity;

import com.sk.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 系统角色实体
 *
 * @author zhangqiao
 * @since 2019-11-11 09:25:07
 */
@Getter
@Setter
@Table(name = "sys_role")
public class SysRole extends BaseEntity {

    /**
	 * 角色名称    
     */
    @Column(name = "name")
    private String name;

    /**
     * 是否默认角色
     */
    @Column(name = "default_role")
    private Boolean defaultRole;

    /**
	 * 描述    
     */
    @Column(name = "description")
    private String description;
                    
}