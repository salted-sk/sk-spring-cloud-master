package com.sk.common.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sk.common.config.page.PageDomain;
import com.sk.common.config.page.TableDataInfo;
import com.sk.common.config.page.TableSupport;
import com.sk.common.config.po.Result;
import com.sk.common.utils.EmptyUtils;

import java.util.List;

/**
 * web层通用数据处理
 *
 * @author zhangqiao
 * @since 2019/12/4 13:01
 */
public class BaseController {

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (EmptyUtils.isNotEmpty(pageNum) && EmptyUtils.isNotEmpty(pageSize)) {
            String orderBy = pageDomain.getOrderBy();
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 返回成功
     */
    public Result success() {
        return Result.success();
    }

    /**
     * 返回成功数据
     */
    public Result success(Object data) {
        return Result.success(data);
    }

    /**
     * 返回失败
     */
    public Result error() {
        return Result.error();
    }

    /**
     * 返回失败消息
     */
    public Result error(String message) {
        return Result.error(message);
    }

    /**
     * 返回错误码消息
     */
    public Result error(int code, String message) {
        return Result.error(code, message);
    }

    /**
     * 根据修改搜影响的行数返回结果
     */
    public Result result(int i) {
        return i > 0 ? success() : error();
    }

}
