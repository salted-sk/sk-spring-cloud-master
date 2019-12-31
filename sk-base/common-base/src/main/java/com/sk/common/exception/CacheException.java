package com.sk.common.exception;

import com.sk.common.config.po.CommonCode;
import com.sk.common.config.po.ResultCode;

/**
 * 缓存异常
 *
 * @author zhangqiao
 * @since 2019/12/17 16:31
 */
public class CacheException extends ApplicationException{

    private static final long serialVersionUID = 1;

    private static final ResultCode RESULT_CODE = CommonCode.CACHE_ERROR;

    public CacheException() {
        super(RESULT_CODE.message());
    }

    public CacheException(String message) {
        super(message);
        this.code = RESULT_CODE.code();
    }

    public CacheException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
        this.code = RESULT_CODE.code();
    }

    public CacheException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CacheException(Throwable cause) {
        super(RESULT_CODE.message(), cause);
        this.code = RESULT_CODE.code();
    }
}

