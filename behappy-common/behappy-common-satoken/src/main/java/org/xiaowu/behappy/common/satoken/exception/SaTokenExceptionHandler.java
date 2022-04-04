package org.xiaowu.behappy.common.satoken.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xiaowu.behappy.common.core.util.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiaowu
 * @apiNote
 */
@Slf4j
@RestControllerAdvice
public class SaTokenExceptionHandler {
    // 全局异常拦截（拦截项目中的SaTokenException异常）
    @ExceptionHandler(SaTokenException.class)
    public Response handlerNotLoginException(SaTokenException nle, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 打印堆栈，以供调试
        log.error("SaTokenExceptionHandler - handlerNotLoginException: {}", nle.getMessage());
        if (nle instanceof NotLoginException) {
            return Response.unauthorized(nle);
        }
        // 返回给前端
        return Response.failed(nle);
    }

}
