package top.keyto.blog.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Keyto
 * Created on 2019/10/6
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        log.info("/ping called");
        return "pong";
    }

    @GetMapping("/r/a1")
    public String r1() {
        log.info("/r/a1 called");
        return "/r/a1";
    }

    @GetMapping("/r/a2")
    public String r2() {
        log.info("/r/a2 called");
        return "/r/a2";
    }

}
