package com.sk.common.exception;

import com.sk.common.config.po.CommonCode;
import com.sk.common.config.po.ResultCode;

/**
 * 无授权异常
 *
 * @author zhangqiao
 * @since 2019/12/17 16:36
 */
public class UnAuthoriseException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    private static final ResultCode RESULT_CODE = CommonCode.UNAUTHORISE;

    public UnAuthoriseException() {
        super(CommonCode.UNAUTHORISE.message());
    }

    public UnAuthoriseException(String message) {
        super(message);
        this.code = RESULT_CODE.code();
    }

    public UnAuthoriseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public UnAuthoriseException(String message, Throwable cause) {
        super(message, cause);
        this.code = RESULT_CODE.code();
    }

    public UnAuthoriseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public UnAuthoriseException(Throwable cause) {
        super(RESULT_CODE.message(), cause);
        this.code = RESULT_CODE.code();
    }
}

