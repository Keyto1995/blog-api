package top.keyto.blog.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.keyto.blog.api.entity.Tag;

import java.util.Set;

/**
 * @author Keyto
 * Created on 2019/11/7
 */
public interface TagDao extends JpaRepository<Tag, Long> {
    Set<Tag> findAllByIdIn(Long[] ids);
}
