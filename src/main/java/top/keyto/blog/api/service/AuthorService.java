package top.keyto.blog.api.service;

import top.keyto.blog.api.entity.Author;

import java.util.List;

/**
 * @author Keyto
 * Created on 2019/11/8
 */
public interface AuthorService {
    Author register(String name, String ownerName);

    Author getBySysUsername(String username);

    List<Author> getAllAuthors();
}
