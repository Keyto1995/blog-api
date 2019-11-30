package top.keyto.blog.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.keyto.blog.api.dao.ArticleDao;
import top.keyto.blog.api.dao.TagDao;
import top.keyto.blog.api.entity.Article;
import top.keyto.blog.api.entity.Tag;
import top.keyto.blog.api.service.ArticleService;

import java.util.Date;

/**
 * @author Keyto
 * Created on 2019/10/29
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDao articleDao;
    private final TagDao tagDao;


    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao, TagDao tagDao) {
        this.articleDao = articleDao;
        this.tagDao = tagDao;
    }

    @Override
    public Article save(Article article) {
        return articleDao.save(article);
    }

    @Override
    public Article getById(Long id) {
        return articleDao.findById(id).orElse(new Article());
    }

    @Override
    public Page<Article> getAllArticles(Pageable pageable) {
        return articleDao.findAll(pageable);
    }

    @Override
    public Page<Article> getPublishedArticles(Pageable pageable) {
        return articleDao.findByPublishDateBefore(new Date(), pageable);
    }

    @Override
    public Long getCountByTagId(Long id) {
        Tag tag = tagDao.getOne(id);
        return articleDao.countByTagsContains(tag);
    }

    @Override
    public void delete(Long id) {
        articleDao.deleteById(id);
    }
}
