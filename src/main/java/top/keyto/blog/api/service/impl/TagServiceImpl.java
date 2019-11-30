package top.keyto.blog.api.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.keyto.blog.api.dao.TagDao;
import top.keyto.blog.api.entity.Tag;
import top.keyto.blog.api.service.TagService;

import java.util.List;
import java.util.Set;

/**
 * @author Keyto
 * Created on 2019/11/10
 */
@Slf4j
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag addTag(String name, String url, String description) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setUrl(url);
        tag.setDescription(description);
        return tagDao.save(tag);
    }

    @Override
    public Tag getById(Long id) {
        return tagDao.getOne(id);
    }

    @Override
    public List<Tag> getTags() {
        return tagDao.findAll();
    }

    @Override
    public Set<Tag> getTagsByIds(Long[] ids) {
        return tagDao.findAllByIdIn(ids);
    }

    @Override
    public Tag putTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    public void deleteById(Long id) {
        tagDao.deleteById(id);
    }
}
