package com.sk.common.exception;

import com.sk.common.config.po.CommonCode;
import com.sk.common.config.po.ResultCode;

/**
 * 数据访问异常
 *
 * @author zhangqiao
 * @since 2019/12/17 15:56
 */
public class DaoException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    private static final ResultCode RESULT_CODE = CommonCode.DAO_ERROR;

    public DaoException() {
        super(RESULT_CODE.message());
    }

    public DaoException(String message) {
        super(message);
        this.code = RESULT_CODE.code();
    }

    public DaoException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
        this.code = RESULT_CODE.code();
    }

    public DaoException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public DaoException(Throwable cause) {
        super(RESULT_CODE.message(), cause);
        this.code = RESULT_CODE.code();
    }
}

