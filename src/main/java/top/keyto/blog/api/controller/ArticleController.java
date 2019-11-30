package top.keyto.blog.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.keyto.blog.api.entity.Article;
import top.keyto.blog.api.entity.Author;
import top.keyto.blog.api.entity.Tag;
import top.keyto.blog.api.service.ArticleService;
import top.keyto.blog.api.service.AuthorService;
import top.keyto.blog.api.service.TagService;

import java.security.Principal;
import java.util.Set;

/**
 * @author Keyto
 * Created on 2019/10/29
 */
@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final AuthorService authorService;
    private final TagService tagService;

    @Autowired
    public ArticleController(ArticleService articleService, AuthorService authorService, TagService tagService) {
        this.articleService = articleService;
        this.authorService = authorService;
        this.tagService = tagService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('AUTHOR')")
    public Article postArticle(Principal principal, @RequestBody Article article) {
        log.debug("[post] /articles/ called: article={}", article);
        article.setId(null);
        String name = principal.getName();
        Author author = authorService.getBySysUsername(name);
        article.setAuthor(author);
        return articleService.save(article);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public Article putArticle(@PathVariable Long id, @RequestBody Article articleDTO) {
        log.debug("[put] /articles/{id} called: id={}, articleDTO={}", id, articleDTO);
        articleDTO.setId(id);
        Article article = articleService.getById(id);
        articleDTO.setAuthor(article.getAuthor());
        return articleService.save(articleDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AUTHOR')")
    public void deleteArticle(@PathVariable Long id) {
        log.debug("[delete] /articles/{id} called: id={}", id);
        articleService.delete(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Article> getAllArticles(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "30") int size,
                                        @RequestParam(defaultValue = "DESC") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(sort.toUpperCase()), "id");
        log.debug("[get] /articles/published called: page={}", pageable);
        return articleService.getAllArticles(pageable);
    }

    @GetMapping("/published")
    public Page<Article> getPublishedArticles(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "30") int size,
                                              @RequestParam(defaultValue = "DESC") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(sort.toUpperCase()), "publishDate");
        log.debug("[get] /articles/published called: page={}", pageable);
        return articleService.getPublishedArticles(pageable);
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable("id") Long id, Model model) {
        log.debug("[get] article called: id={}", id);
        Article article = articleService.getById(id);
        model.addAttribute("article", article);
        return article;
    }

    @PutMapping("/{id}/tags/")
    public Article putTags(@PathVariable("id") Long id, @RequestBody Long[] tagIds) {
        log.debug("[put] /articles/{id}/tags/ called: id={}, tagIds={}", id, tagIds);
        Article article = articleService.getById(id);
        Set<Tag> tags = tagService.getTagsByIds(tagIds);
        article.setTags(tags);
        articleService.save(article);
        return article;
    }

    @GetMapping("/countByTag")
    public Long getCountByTag(@RequestParam("id") Long id) {
        return articleService.getCountByTagId(id);
    }
}
