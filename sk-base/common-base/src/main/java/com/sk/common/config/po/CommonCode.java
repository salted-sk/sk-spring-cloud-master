package com.sk.common.config.po;

/**
 * 返回状态码
 *
 * @author zhangqiao
 * @since 2019/12/17 16:18
 */
public enum CommonCode implements ResultCode {

    /**
     * 成功及相关code
     */
    SUCCESS(true, 0, "success"),

    /**
     * 未知错误及相关code
     */
    ERROR(false, 999, "未知错误！"),

    /**
     * 应用异常及相关code
     */
    APPLICATION_ERROR(false, 900, "应用异常！"),

    /**
     * 其它异常及相关code
     */
    SERVICE_ERROR(false, 901, "系统繁忙，请稍后重试！"),

    /**
     * 数据库异常及相关code
     */
    DAO_ERROR(false, 902, "数据库繁忙，请稍后重试！"),

    /**
     * 缓存异常及相关code
     */
    CACHE_ERROR(false, 903, "缓存异常！"),

    /**
     * 验证码错误及相关code
     */
    VALIDATE_ERROR(false, 904, "验证码错误！"),

    /**
     * 用户不存在或密码错误及相关code
     */
    LOGIN_ERROR(false,400, "用户不存在或密码错误！"),

    /**
     * 权限不足及相关code
     */
    UNAUTHORISE(false,401, "权限不足！");

    //操作是否成功
    private boolean success;
    //操作代码
    private int code;
    //提示信息
    private String message;

    CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return "success=" + success +
                ", code=" + code +
                ", message=" + message;
    }
}
