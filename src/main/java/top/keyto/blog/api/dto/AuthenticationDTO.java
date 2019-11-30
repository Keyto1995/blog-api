package top.keyto.blog.api.dto;

import lombok.Data;

/**
 * 用于接收认证数据的传输对象
 *
 * @author Keyto
 * Created on 2019/11/20
 */
@Data
public class AuthenticationDTO {
    private String username;
    private String password;
}
