package com.sk.common.exception;

import com.sk.common.config.po.CommonCode;
import com.sk.common.config.po.ResultCode;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/11/28 10:08
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -2678203134198782909L;

    private static final ResultCode RESULT_CODE = CommonCode.SUCCESS;

    protected int code = RESULT_CODE.code();

    public ApplicationException() {
        super(RESULT_CODE.message());
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
