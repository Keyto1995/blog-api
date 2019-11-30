package top.keyto.blog.api.wrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.keyto.blog.api.dto.ResultDTO;

/**
 * 统一包装数据，暂时不启用
 *
 * @author Keyto
 * Created on 2019/11/21
 */
@Slf4j
//@RestControllerAdvice(basePackages = "top.keyto", annotations = RestController.class)
public class ResultWrapper implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !converterType.isAssignableFrom(StringHttpMessageConverter.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ResultDTO) {
            return body;
        }
        ResultDTO<Object> resultDTO = new ResultDTO<>();
        resultDTO.setCode("0");
        resultDTO.setData(body);

        log.debug("wrapper:{}", resultDTO);
        return resultDTO;
    }
}
