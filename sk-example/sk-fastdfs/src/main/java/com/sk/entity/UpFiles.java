package com.sk.entity;

import com.sk.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 实体
 *
 * @author zhangqiao
 * @since 2019-12-04 13:40:59
 */
@Table(name = "up_files")
public class UpFiles extends BaseEntity {

    /**
	 *     
     */
    @Column(name = "name")
    private String name;

    /**
	 *     
     */
    @Column(name = "url")
    private String url;
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
            
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
                    
}