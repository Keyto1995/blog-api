package top.keyto.blog.api.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import top.keyto.blog.api.entity.Article;
import top.keyto.blog.api.entity.Tag;

import java.util.Date;

/**
 * @author Keyto
 * Created on 2019/10/29
 */
public interface ArticleDao extends JpaRepository<Article, Long> {
    Long countByTagsContains(Tag tag);

    Page<Article> findByPublishDateBefore(Date now, Pageable pageable);
}
