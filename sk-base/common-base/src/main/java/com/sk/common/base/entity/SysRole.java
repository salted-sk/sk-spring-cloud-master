package com.sk.common.base.entity;

import com.sk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 系统角色实体
 *
 * @author zhangqiao
 * @since 2019-11-11 09:25:07
 */
@Table(name = "sys_role")
@Data
public class SysRole extends BaseEntity<Integer> {

    /**
	 * 角色名称    
     */
    @Column(name = "name")
    private String name;

    /**
	 * 描述    
     */
    @Column(name = "description")
    private String description;
                    
}