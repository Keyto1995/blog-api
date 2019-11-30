package top.keyto.blog.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyto.blog.api.entity.SysUser;
import top.keyto.blog.api.service.UserService;

import java.util.Map;

/**
 * @author Keyto
 * Created on 2019/11/8
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public SysUser registerUser(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");
        log.debug("[post] /users/ called: username={}, password={}", username, password);

        return userService.register(username, password);
    }
}
