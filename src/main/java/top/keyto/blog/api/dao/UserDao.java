package top.keyto.blog.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.keyto.blog.api.entity.SysUser;

import java.util.Optional;

/**
 * @author Keyto
 * Created on 2019/10/28
 */
public interface UserDao extends JpaRepository<SysUser, Long> {
    Optional<SysUser> findByUsername(String username);
}
