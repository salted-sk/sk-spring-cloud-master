package com.sk.common.exception;

import com.sk.common.config.po.CommonCode;
import com.sk.common.config.po.ResultCode;

/**
 * 服务处理异常
 *
 * @author zhangqiao
 * @since 2019/12/17 16:36
 */
public class ServiceException extends ApplicationException {

    private static final long serialVersionUID = -2678203134198782909L;

    private static final ResultCode RESULT_CODE = CommonCode.SERVICE_ERROR;

    public ServiceException() {
        super(CommonCode.SERVICE_ERROR.message());
    }

    public ServiceException(String message) {
        super(message);
        this.code = RESULT_CODE.code();
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.code = RESULT_CODE.code();
    }

    public ServiceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Throwable cause) {
        super(RESULT_CODE.message(), cause);
        this.code = RESULT_CODE.code();
    }
}

