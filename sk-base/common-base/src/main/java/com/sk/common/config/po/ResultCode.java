package com.sk.common.config.po;

/**
 * 返回状态码
 *
 * @author zhangqiao
 * @since 2019/12/17 16:31
 */
public interface ResultCode {

    //操作是否成功,true为成功，false操作失败
    boolean success();
    //操作代码
    int code();
    //提示信息
    String message();

}
