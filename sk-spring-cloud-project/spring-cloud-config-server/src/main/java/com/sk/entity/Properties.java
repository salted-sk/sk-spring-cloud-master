package com.sk.entity;

import com.sk.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 实体
 *
 * @author zhangqiao
 * @since 2019-12-12 11:11:39
 */
@Table(name = "properties")
public class Properties extends BaseEntity {

    /**
	 *     
     */
    @Column(name = "`key`")
    private String key;

    /**
	 *     
     */
    @Column(name = "`value`")
    private String value;

    /**
	 *     
     */
    @Column(name = "application")
    private String application;

    /**
	 *     
     */
    @Column(name = "profile")
    private String profile;

    /**
	 *     
     */
    @Column(name = "label")
    private String label;

    /**
     *
     */
    @Column(name = "remark")
    private String remark;
        
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
            
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
            
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
            
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
            
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}