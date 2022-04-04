package org.xiaowu.behappy.common.core.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xiaowu.behappy.common.core.exception.BeHappyException;
import org.xiaowu.behappy.common.core.exception.DeniedException;
import org.xiaowu.behappy.common.core.util.Response;

/**
 * @author 小五
 */
@Slf4j
@RestControllerAdvice(basePackages = "org.xiaowu.behappy")
public class DefaultExceptionHandlerConfig {

    @ExceptionHandler(BeHappyException.class)
    public Response beHappyExceptionHandler(BeHappyException e) {
        log.error("DefaultExceptionHandlerConfig - beHappyExceptionHandler: {}", e.getMsg());
        return Response.failed(e.getCode(), e.getMsg());
    }

    /**
     * 未认证或者认证已过期
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response deniedExceptionHandler(DeniedException e) {
        log.error("DefaultExceptionHandlerConfig - deniedExceptionHandler: {}", e.getMsg());
        return Response.failed(e);
    }

    @ExceptionHandler(Exception.class)
    public Response exceptionHandler(Exception e) {
        log.error("DefaultExceptionHandlerConfig - exceptionHandler: {}", e.getLocalizedMessage());
        return Response.failed(e);
    }
}