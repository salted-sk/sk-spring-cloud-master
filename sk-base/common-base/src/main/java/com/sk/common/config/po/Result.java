package com.sk.common.config.po;

import lombok.Data;

/**
 * 返回结果处理
 *
 * @author zhangqiao
 * @since 2019/12/4 13:01
 */
@Data
public class Result {

    //操作代码
    private int code;

    //提示信息
    private String message;

    //返回结果
    private Object data;

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static Result error() {
        return error(CommonCode.APPLICATION_ERROR.code(), CommonCode.APPLICATION_ERROR.message());
    }

    /**
     * 返回错误消息
     *
     * @param msg 内容
     * @return 错误消息
     */
    public static Result error(String msg) {
        return error(CommonCode.APPLICATION_ERROR.code(), msg);
    }

    /**
     * 返回错误消息
     *
     * @param code 错误码
     * @param msg 内容
     * @return 错误消息
     */
    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    /**
     * 返回成功消息
     *
     * @param msg 内容
     * @return 成功消息
     */
    public static Result success(String msg, Object data) {
        Result result = new Result();
        result.setCode(CommonCode.SUCCESS.code());
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static Result success() {
        return Result.success(CommonCode.SUCCESS.message(), null);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static Result success(Object data) {
        return Result.success(CommonCode.SUCCESS.message(), data);
    }

}
