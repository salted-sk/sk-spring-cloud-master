package com.sk.common.base.entity;

import com.sk.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 系统权限实体
 *
 * @author zhangqiao
 * @since 2019-11-11 09:25:06
 */
@Table(name = "sys_permission")
@Data
public class SysPermission extends BaseEntity {

    /**
	 *     
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
	 * 权限名称    
     */
    @Column(name = "name")
    private String name;

    /**
	 * 权限代码    
     */
    @Column(name = "url")
    private String url;

    /**
	 * 描述    
     */
    @Column(name = "description")
    private String description;

}