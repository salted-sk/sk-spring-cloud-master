package com.sk.common.exception;

import com.sk.common.config.po.Result;
import com.sk.common.config.po.CommonCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 返回错误处理
 *
 * @author zhangqiao
 * @since 2019/12/17 16:32
 */
@RestControllerAdvice
public class AppExceptionHandler {

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex) {
        Result result = new Result();
        result.setCode(CommonCode.ERROR.code());
        result.setMessage(CommonCode.ERROR.message());
        return result;
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ApplicationException.class)
    public Result myErrorHandler(ApplicationException ex) {
        Result result = new Result();
        result.setCode(ex.getCode());
        result.setMessage(ex.getMessage());
        return result;
    }

}
