package top.keyto.blog.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.keyto.blog.api.entity.Article;

/**
 * @author Keyto
 * Created on 2019/10/29
 */
public interface ArticleService {
    Article save(Article article);

    Article getById(Long id);

    Page<Article> getAllArticles(Pageable pageable);

    Page<Article> getPublishedArticles(Pageable pageable);

    Long getCountByTagId(Long id);

    void delete(Long id);
}
