package com.sk.common.config.page;

import com.sk.common.utils.EmptyUtils;
import lombok.Data;

/**
 * 分页数据
 *
 * @author zhangqiao
 * @since 2019/12/17 16:18
 */
@Data
public class PageDomain {

    /**
     * 当前记录起始索引
     */
    private Integer pageNum;

    /**
     * 每页显示记录数
     */
    private Integer pageSize;

    /**
     * 排序列
     */
    private String orderByColumn;

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    private String isAsc;

    public String getOrderBy() {
        if (EmptyUtils.isEmpty(orderByColumn)) {
            return "";
        }
        return orderByColumn + " " + isAsc;
    }

}
