package top.keyto.blog.api.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.keyto.blog.api.dto.ResultDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Keyto
 * Created on 2019/11/21
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object exceptionHandler(HttpServletRequest request, Exception ex, HttpServletResponse response) {
        log.error("全局异常处理：", ex);
        return ResultDTO.FAILE("-1", ex.getLocalizedMessage(), null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Object accessDeniedExceptionHandler(HttpServletRequest request, Exception ex, HttpServletResponse response) throws Exception {
        log.error("认证全局异常处理：", ex);
        throw ex;
//        return ResultDTO.FAILE("-1", ex.getLocalizedMessage(), null);
    }
}
