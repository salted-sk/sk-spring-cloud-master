package com.sk.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * 基础信息
 * @author zhangqiao
 * @since 2019/8/31 20:12
 */
@Data
public class BaseEntity<K> {

    /**
     * 主键
     */
    @Id
    @OrderBy("desc")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private K id;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 删除状态 0正常 1删除
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

}
