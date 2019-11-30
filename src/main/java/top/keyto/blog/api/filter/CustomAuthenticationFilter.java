package top.keyto.blog.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.keyto.blog.api.dto.AuthenticationDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 处理JSON格式请求的认证过滤器
 *
 * @author Keyto
 * Created on 2019/11/20
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 当Content-Type为json时尝试认证
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
            || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            // 使用jackson反序列化json
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest;
            try (InputStream is = request.getInputStream()) {
                AuthenticationDTO authenticationDTO = mapper.readValue(is, AuthenticationDTO.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                    authenticationDTO.getUsername(), authenticationDTO.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            }
            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }

        // 发送给UsernamePasswordAuthenticationFilter处理
        else {
            return super.attemptAuthentication(request, response);
        }
    }
}
