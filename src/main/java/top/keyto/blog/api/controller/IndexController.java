package top.keyto.blog.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.keyto.blog.api.service.ArticleService;

/**
 * @author Keyto
 * Created on 2019/10/31
 */
@Controller
public class IndexController {

    private final ArticleService articleService;

    @Autowired
    public IndexController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") int page,
                        Model model) {
        Pageable pageable = PageRequest.of(page, 30, Sort.Direction.DESC, "publishDate");
        model.addAttribute("articles", articleService.getPublishedArticles(pageable).getContent());
        return "index";
    }
}
