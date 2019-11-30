package top.keyto.blog.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.keyto.blog.api.entity.Author;

/**
 * @author Keyto
 * Created on 2019/11/7
 */
public interface AuthorDao extends JpaRepository<Author, Long> {
    Author getByOwner_Username(String username);
}
