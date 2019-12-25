package com.sk.common.config.page;

import com.sk.common.config.po.Constants;
import com.sk.common.utils.ServletUtils;

/**
 * 表格数据处理
 *
 * @author zhangqiao
 * @since 2019/12/17 16:18
 */
public class TableSupport {
    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGENUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGESIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDERBYCOLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(Constants.ISASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }

}
