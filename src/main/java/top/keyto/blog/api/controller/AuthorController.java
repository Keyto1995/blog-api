package top.keyto.blog.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyto.blog.api.entity.Author;
import top.keyto.blog.api.service.AuthorService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author Keyto
 * Created on 2019/11/10
 */
@Slf4j
@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Author postAuthor(Principal principal, @RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        log.debug("[post] /authors/ called : principal={}, name={}", principal.getName(), name);
        return authorService.register(name, principal.getName());
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Author> getAuthors() {
        log.debug("[get] /authors/ called :");
        return authorService.getAllAuthors();
    }
}
