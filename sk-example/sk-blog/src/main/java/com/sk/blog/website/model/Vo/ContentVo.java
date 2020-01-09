package com.sk.blog.website.model.Vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@Data
public class ContentVo implements Serializable {
    /**
     * post表主键
     */
    private Integer cid;

    /**
     * 内容标题
     */
    private String title;

    /**
     * 内容缩略名
     */
    private String slug;

    /**
     * 内容生成时的GMT unix时间戳
     */
    private Integer created;

    /**
     * 内容更改时的GMT unix时间戳
     */
    private Integer modified;

    /**
     * 内容所属用户id
     */
    private Integer authorId;

    /**
     * 内容类别
     */
    private String type;

    /**
     * 内容状态
     */
    private String status;

    /**
     * 标签列表
     */
    private String tags;

    /**
     * 分类列表
     */
    private String categories;

    /**
     * 点击次数
     */
    private Integer hits;

    /**
     * 内容所属评论数
     */
    private Integer commentsNum;

    /**
     * 是否允许评论
     */
    private Boolean allowComment;

    /**
     * 是否允许ping
     */
    private Boolean allowPing;

    /**
     * 允许出现在聚合中
     */
    private Boolean allowFeed;

    /**
     * 内容文字
     */
    private String content;

    private String imageUrl;

    private static final long serialVersionUID = 1L;
}