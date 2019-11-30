package top.keyto.blog.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.keyto.blog.api.entity.Tag;
import top.keyto.blog.api.service.TagService;

import java.util.List;
import java.util.Map;

/**
 * @author Keyto
 * Created on 2019/11/10
 */
@Slf4j
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/")
    public List<Tag> getTags() {
        log.debug("[get] /tags/ called :");
        return tagService.getTags();
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Tag postTag(@RequestBody Map<String, String> tag) {
        String name = tag.get("name");
        String url = tag.get("url");
        String description = tag.get("description");
        log.debug("[post] /tags/ called : name={}, url={}, description={}", name, url, description);
        return tagService.addTag(name, url, description);
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Tag putTag(@RequestBody Tag tag) {
        log.debug("[put] /tags/ called : tag={}", tag);
        return tagService.putTag(tag);
    }

    @GetMapping("/{id}")
    public Tag getTag(@PathVariable Long id) {
        log.debug("[get] /tags/{id} called : id={}", id);
        return tagService.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTag(@PathVariable Long id) {
        log.debug("[delete] /tags/{id} called : id={}", id);
        tagService.deleteById(id);
    }
}
