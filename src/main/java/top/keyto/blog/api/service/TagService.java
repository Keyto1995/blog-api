package top.keyto.blog.api.service;

import top.keyto.blog.api.entity.Tag;

import java.util.List;
import java.util.Set;

/**
 * @author Keyto
 * Created on 2019/11/10
 */
public interface TagService {
    Tag addTag(String name, String url, String description);

    Tag getById(Long id);

    List<Tag> getTags();

    Set<Tag> getTagsByIds(Long[] ids);

    Tag putTag(Tag tag);

    void deleteById(Long id);
}
