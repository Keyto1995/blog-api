package top.keyto.blog.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import top.keyto.blog.api.dao.AuthorDao;
import top.keyto.blog.api.dao.UserDao;
import top.keyto.blog.api.entity.Author;
import top.keyto.blog.api.entity.SysRole;
import top.keyto.blog.api.entity.SysUser;
import top.keyto.blog.api.exception.RegisterException;
import top.keyto.blog.api.service.AuthorService;

import java.util.List;
import java.util.Optional;

/**
 * @author Keyto
 * Created on 2019/11/8
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;
    private final UserDao userDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao, UserDao userDao) {
        this.authorDao = authorDao;
        this.userDao = userDao;
    }

    @Override
    public Author register(String name, String ownerName) {
        Author author = new Author();
        author.setName(name);
        Optional<SysUser> optionalSysUser = userDao.findByUsername(ownerName);
        if (!optionalSysUser.isPresent()) {
            throw new RegisterException("未注册账号");
        }
        SysUser sysUser = optionalSysUser.get();
        author.setOwner(sysUser);
        // 检查是否重复注册作者
        if (authorDao.exists(Example.of(author, ExampleMatcher.matchingAny().withIgnoreCase()))) {
            throw new RegisterException("此账号已注册作者 或 作者名已被注册");
        }

        // 为用户添加作者角色
        SysRole sysRole = new SysRole();
        sysRole.setId(3L);
        sysUser.getRoles().add(sysRole);
        userDao.save(sysUser);
        return authorDao.save(author);
    }

    @Override
    public Author getBySysUsername(String username) {
        return authorDao.getByOwner_Username(username);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }
}
