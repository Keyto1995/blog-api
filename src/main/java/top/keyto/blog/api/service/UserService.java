package top.keyto.blog.api.service;

import top.keyto.blog.api.entity.SysUser;

/**
 * @author Keyto
 * Created on 2019/10/29
 */
public interface UserService {
    SysUser register(String username, String password);
}
